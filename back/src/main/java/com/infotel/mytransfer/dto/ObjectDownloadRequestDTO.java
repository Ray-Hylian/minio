package com.infotel.mytransfer.dto;

/**
 * Data Transfer Object containing informations for requesting
 * an object download.
 */
public class ObjectDownloadRequestDTO implements DTO {
	
	private String bucket;
	private String path;
	
	public ObjectDownloadRequestDTO(String bucket, String path) {
		this.bucket = bucket;
		this.path = path;
	}

	public ObjectDownloadRequestDTO() {
	}

	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	

}
