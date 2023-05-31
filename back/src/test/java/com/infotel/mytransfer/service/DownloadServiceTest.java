package com.infotel.mytransfer.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.infotel.mytransfer.entities.ObjectDownloadEntity;
import com.infotel.mytransfer.entities.ObjectInfoEntity;
import com.infotel.mytransfer.repository.DownloadRepository;
import com.infotel.mytransfer.service.impl.DownloadServiceImpl;

@ExtendWith(MockitoExtension.class)
public class DownloadServiceTest {
	
	@Mock
	private DownloadRepository downloadRepository;
	
	@Mock
	private DataObjectService objectService;
	
	@Mock
	private SignatureService signatureService;
	
	@InjectMocks
	private DownloadServiceImpl downloadService;
	
	/**
	 * Tests {@link DownloadServiceImpl#createDownloadRequest(String, String)}<br/>
	 * Tautology
	 */
	@Test
	public void testCreateDownloadRequest() {
		String uuid = UUID.randomUUID().toString();
		String bucket = UUID.randomUUID().toString();
		String path = "path-to-file.extension";
		
		ObjectDownloadEntity mock = new ObjectDownloadEntity(uuid, bucket, path);
		
		Mockito.when(downloadRepository.save(Mockito.any(ObjectDownloadEntity.class))).thenReturn(mock);
		
		ObjectDownloadEntity test = downloadService.createDownloadRequest(bucket, path);
		
		assertEquals(mock, test);
	}
	
	/**
	 * Tests {@link DownloadServiceImpl#getObjectDownloadRequest(String)}
	 */
	@Test
	public void testGetObjectDownloadRequest() {
		String uuid = UUID.randomUUID().toString();
		String bucket = UUID.randomUUID().toString();
		String path = "path-to-file.extension";
		
		ObjectDownloadEntity mock = new ObjectDownloadEntity(uuid, bucket, path);
		
		Mockito.when(downloadRepository.findDistinctByUuid(uuid)).thenReturn(mock);
		
		ObjectDownloadEntity test = downloadService.getObjectDownloadRequest(uuid);
		
		assertEquals(mock, test);
	}
	
	/**
	 * Tests for {@link DownloadServiceImpl#downloadSignedObject(ObjectDownloadEntity)}<br/>
	 * Success means data has been transferred successfully through ResponseStream.
	 */
	@Test
	public void testDownloadSignedObject() throws IOException {
		byte[] mockBytes = "test de contenu de fichier".getBytes();
		InputStream mockInputStream = new ByteArrayInputStream(mockBytes);
		
		Mockito.when(objectService.getObject(Mockito.anyString(), Mockito.anyString())).thenReturn(mockInputStream);
		
		ObjectInfoEntity mockObjectInfo = Mockito.mock(ObjectInfoEntity.class);
		Mockito.when(mockObjectInfo.getSize()).thenReturn(Long.valueOf(mockBytes.length));
		Mockito.when(objectService.getObjectInfo(Mockito.anyString(), Mockito.anyString())).thenReturn(mockObjectInfo);
		
		Mockito.when(signatureService.beginSignature()).thenReturn(null);
		Mockito.doNothing().when(signatureService).updateSigature(Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(signatureService.endSignature(Mockito.any())).thenReturn(new byte[] {});
		
		ObjectDownloadEntity ode = new ObjectDownloadEntity(0L, UUID.randomUUID().toString(), "test-bucket", "test-file.txt");
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		downloadService.downloadSignedObject(ode).writeTo(os);
		
		assertArrayEquals(mockBytes, os.toByteArray());
	}

}
