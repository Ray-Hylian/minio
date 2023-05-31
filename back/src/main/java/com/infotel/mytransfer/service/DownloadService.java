package com.infotel.mytransfer.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.infotel.mytransfer.entities.ObjectDownloadEntity;
import com.infotel.mytransfer.entities.SignedObjectEntity;

@Service
public interface DownloadService  {
	
	/**
	 * Creates a download request.
	 * 
	 * @param bucket which contains objects.
	 * @param path is the position of the object in the bucket.
	 * @return a {@link ObjectDownloadEntity} instance.
	 */
	public ObjectDownloadEntity createDownloadRequest(String bucket, String path);

	/**
	 * Retrieves a {@link ObjectDownloadEntity} from the database.
	 * 
	 * @param uuid The uuid of the {@link ObjectDownloadEntity} to retrieve.
	 * @return a {@link ObjectDownloadEntity} instance.
	 */
	public ObjectDownloadEntity getObjectDownloadRequest(String uuid);
	
	/**
	 * Downloads a signed object.
	 * 
	 * @param objectDownloadEntity a {@link ObjectDownloadEntity} containing download information.
	 * @return a {@link SignedObjectEntity} instance containing the object's data and it's signature.
	 */
	public StreamingResponseBody downloadSignedObject(ObjectDownloadEntity objectDownloadEntity);
}
