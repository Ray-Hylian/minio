package com.infotel.mytransfer.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Signature;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.infotel.mytransfer.entities.ObjectDownloadEntity;
import com.infotel.mytransfer.entities.ObjectInfoEntity;
import com.infotel.mytransfer.repository.DownloadRepository;
import com.infotel.mytransfer.service.DataObjectService;
import com.infotel.mytransfer.service.DownloadService;
import com.infotel.mytransfer.service.SignatureService;

@Service
public class DownloadServiceImpl implements DownloadService {

	private static Logger LOGGER = LoggerFactory.getLogger(DownloadServiceImpl.class);
	
	@Autowired
	private DataObjectService objectService;
	
	@Autowired
	private SignatureService signatureService;
	
	@Autowired
	private DownloadRepository downloadRepository;
	
	@Override
	public ObjectDownloadEntity createDownloadRequest(String bucket, String path) {
		String uuid = UUID.randomUUID().toString();
		
		ObjectDownloadEntity objectDownloadEntity = new ObjectDownloadEntity();
		objectDownloadEntity.setUuid(uuid);
		objectDownloadEntity.setBucket(bucket);
		objectDownloadEntity.setPath(path);
		return downloadRepository.save(objectDownloadEntity);
	}

	@Override
	public ObjectDownloadEntity getObjectDownloadRequest(String uuid) {
		return downloadRepository.findDistinctByUuid(uuid);
	}
	
	@Override
	public StreamingResponseBody downloadSignedObject(ObjectDownloadEntity objectDownloadEntity) {
		InputStream is = objectService.getObject(objectDownloadEntity.getBucket(), objectDownloadEntity.getPath());
		ObjectInfoEntity info = objectService.getObjectInfo(objectDownloadEntity.getBucket(), objectDownloadEntity.getPath());
		
		final int partSize = (int) (info.getSize() > 2048 ? 2048 : info.getSize());
		
		Signature signature = signatureService.beginSignature();

		byte[] buffer = new byte[partSize];
		return new StreamingResponseBody() {
			@Override
			public void writeTo(OutputStream outputStream) throws IOException {
				long totalRead = 0;

				while(totalRead < info.getSize()) {
					int read = is.read(buffer);
					totalRead += read;
					
					signatureService.updateSigature(signature, buffer, 0, read);
					outputStream.write(buffer, 0, read);
					outputStream.flush();
				}
				
				byte[] sig = signatureService.endSignature(signature);
				
				outputStream.write(sig);
				outputStream.flush();
				
				is.close();
			}
		};
	}
	
}
