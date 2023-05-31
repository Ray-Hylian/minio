package com.infotel.mytransfer.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.infotel.mytransfer.dto.MetadataDTO;
import com.infotel.mytransfer.entities.BucketObjectInfoEntity;
import com.infotel.mytransfer.entities.DataObjectEntity;
import com.infotel.mytransfer.entities.ObjectInfoEntity;
import com.infotel.mytransfer.service.impl.MinioServiceImpl;

import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.StatObjectResponse;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Item;

@ExtendWith(MockitoExtension.class)
public class MinioServiceTest {

	@Mock
	private MinioClient minioClient;
	
	@InjectMocks
	private MinioServiceImpl minioService;
	
	/**
	 * Tests {@link DataObjectService#uploadObject(String, java.util.Collection, MetadataDTO[])}<br/>
	 * Success if NullPointerException is raised because of bucketName
	 */
	@Test
	public void testUploadObjectNull() {
		assertThrows(NullPointerException.class, () -> minioService.uploadObject(null, Collections.emptyList(), new MetadataDTO[0]));
	}
	
	/**
	 * Tests {@link DataObjectService#uploadObject(String, java.util.Collection, MetadataDTO[])}<br/>
	 * Success if NullPointerException is raised because of dataObjects
	 */
	@Test
	public void testUploadObjectNull2() {
		assertThrows(NullPointerException.class, () -> minioService.uploadObject("", null, new MetadataDTO[0]));
	}
	
	/**
	 * Tests {@link DataObjectService#uploadObject(String, java.util.Collection, MetadataDTO[])}<br/>
	 * Success if all objects are uploaded.
	 */
	@Test
	public void testUploadObject() {
		try {
			int n = 3;
			List<DataObjectEntity> objects = Collections.nCopies(n, new DataObjectEntity("sqdzqd", InputStream.nullInputStream(), 0, "text/plain"));

			Mockito.when(minioClient.putObject(Mockito.any())).then(i -> null);
			
			assertTrue(minioService.uploadObject("aaaa", objects, null));
			
			Mockito.verify(minioClient, Mockito.times(n)).putObject(Mockito.any());
		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
				| IOException e) {
			fail("Cannot upload objects");
		}
	}
	
	/**
	 * Tests {@link DataObjectService#uploadObject(String, java.util.Collection, MetadataDTO[])}<br/>
	 * Success if no objects are uploaded because of an IOException.
	 */
	@Test
	public void testUploadObject2() {
		try {
			int n = 3;
			List<DataObjectEntity> objects = Collections.nCopies(n, new DataObjectEntity("sqdzqd", InputStream.nullInputStream(), 0, "text/plain"));
			
			Mockito.when(minioClient.putObject(Mockito.any())).thenThrow(IOException.class);
			
			assertFalse(minioService.uploadObject("aaaa", objects, null));
		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
				| IOException e) {
			fail("Cannot fail object upload");
		}
	}
	
	/**
	 * Tests {@link DataObjectService#createBucket(String)}<br/>
	 * Success if NullPointerException is raised because of bucketName
	 */
	@Test
	public void testCreateBucketNull() {
		assertThrows(NullPointerException.class, () -> minioService.createBucket(null));
	}
	
	/**
	 * Tests {@link DataObjectService#createBucket(String)}<br/>
	 * Success if bucket created successfully.
	 */
	@Test
	public void testCreateBucket() {
		try {
			String bucketName = "bucketname";
			
			Mockito.doNothing().when(minioClient).makeBucket(Mockito.any());
			Mockito.doNothing().when(minioClient).setBucketVersioning(Mockito.any());
			
			assertTrue(minioService.createBucket(bucketName));
			
			Mockito.verify(minioClient, Mockito.times(1)).makeBucket(Mockito.any());
			Mockito.verify(minioClient, Mockito.times(1)).setBucketVersioning(Mockito.any());			
		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
				| IOException e) {
			fail("Cannot create bucket");
		}
	}
	
	/**
	 * Tests {@link DataObjectService#createBucket(String)}<br/>
	 * Success if no bucket created because of an IOException.
	 */
	@Test
	public void testCreateBucket2() {
		try {
			String bucketName = "bucketname";
			
			Mockito.doThrow(IOException.class).when(minioClient).makeBucket(Mockito.any());
			
			assertFalse(minioService.createBucket(bucketName));
			
			Mockito.verify(minioClient, Mockito.never()).setBucketVersioning(Mockito.any());
		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
				| IOException e) {
			fail("Cannot create bucket");
		}
	}
	
	/**
	 * Tests {@link DataObjectService#getListObjects(String)}<br/>
	 * Success if no bucket created because of an IOException.
	 */
	@Test
	public void testGetListObjects() {
		int n = 5;
		String bucketName = "bucket-name";
		String objectName = "object-name";
		
		Item item = Mockito.mock(Item.class);
		List<Result<Item>> items = Collections.nCopies(n, new Result<Item>(item));
		
		Mockito.when(minioClient.listObjects(Mockito.any())).thenReturn(items);
		Mockito.when(item.objectName()).then(i -> objectName);
		
		List<BucketObjectInfoEntity> test = minioService.getListObjects(bucketName);
		
		assertTrue(test.stream().allMatch(o -> o.getName().equals(objectName)));
	}
	
	/**
	 * Tests {@link DataObjectService#getObjectInfo(String)}
	 */
	@Test
	public void testObjectInfo() {
		try {
			ObjectInfoEntity entity = new ObjectInfoEntity("etag", 0L, ZonedDateTime.now(), ZonedDateTime.now(), false, Collections.emptyMap(), "1.0", "plain/text");
			StatObjectResponse mock = Mockito.mock(StatObjectResponse.class);
			
			Mockito.when(mock.etag()).thenReturn(entity.getEtag());
			Mockito.when(mock.size()).thenReturn(entity.getSize());
			Mockito.when(mock.lastModified()).thenReturn(entity.getLastModified());
			Mockito.when(mock.retentionRetainUntilDate()).thenReturn(entity.getRetentionRetainUntilDate());
			Mockito.when(mock.deleteMarker()).thenReturn(entity.isDeleteMarker());
			Mockito.when(mock.userMetadata()).thenReturn(entity.getUserMetadata());
			Mockito.when(mock.versionId()).thenReturn(entity.getVersionId());
			Mockito.when(mock.contentType()).thenReturn(entity.getContentType());
			
			Mockito.when(minioClient.statObject(Mockito.any())).thenReturn(mock);
			
			assertThat(entity.equals(minioService.getObjectInfo("bucket-name", "file-path")));
		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
				| IOException e) {
			fail("Cannot get object info");
		}
	}
	
	/**
	 * Tests {@link DataObjectService#getObject(String, String)}
	 */
	@Test
	public void testGetObject() {
		try {
			InputStream is = new ByteArrayInputStream("input stream test".getBytes());
			GetObjectResponse gor = new GetObjectResponse(null, null, null, null, is);

			Mockito.when(minioClient.getObject(Mockito.any())).thenReturn(gor);
			
			assertEquals(gor, minioService.getObject("bucket-name", "object-path"));
		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
				| IOException e) {
			fail("Cannot get object");
		}
		
	}
	
}
