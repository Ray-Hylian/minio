package com.infotel.mytransfer.service;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import com.infotel.mytransfer.dto.MetadataDTO;
import com.infotel.mytransfer.entities.BucketObjectInfoEntity;
import com.infotel.mytransfer.entities.DataObjectEntity;
import com.infotel.mytransfer.entities.ObjectInfoEntity;

public interface DataObjectService {
	
	/**
	 * Upload an object on data object service. 
	 * @param bucket that will contains objects
	 * @param dataObjects data objects uploaded by users.
	 * @param metadata Metadata values to upload with the objects
	 * @return true if successful, false otherwise.
	 */
	public boolean uploadObject(String bucket, Collection<DataObjectEntity> dataObjects, MetadataDTO[] metadata);
	
	/**
	 * Creates a bucket in the data object service.
	 * @param bucketName The name to give to the bucket.
	 */
	public boolean createBucket(String bucketName);
	
	/**
	 * Get objects from a bucket.
	 * @param bucketName selected bucket.
	 * @return list of the objects present in a bucket.
	 */
	public List<BucketObjectInfoEntity> getListObjects(String bucketName);
	
	/**
	 * Gets information about an object.
	 * 
	 * @param bucketName The bucket where to seek for the object.
	 * @param path The path of the object.
	 * @return A {@code ObjectInfoEntity} instance containing the object's information or null.
	 */
	public ObjectInfoEntity getObjectInfo(String bucketName, String path);
	
	/**
	 * Gets the object from MinIO an into an {@code InputStream}.
	 * 
	 * @param bucketName The bucket where to seek for the object.
	 * @param path The path of the object
	 * @return An InputStream with the object's data
	 */
	public InputStream getObject(String bucketName, String path);

}