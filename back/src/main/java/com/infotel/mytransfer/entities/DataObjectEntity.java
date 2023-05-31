package com.infotel.mytransfer.entities;

import java.io.InputStream;

/**
 * Entity holding data before sending it to the data object service.
 */
public class DataObjectEntity implements Entity {

	private String path;
	private InputStream inputStream;
	private long size;
	private String contentType;
	
	/**
	 * @param path The path and name to give this object in the bucket
	 * @param inputStream The stream of data.
	 * @param size the size of this object's data (in Bytes).
	 * @param contentType The content type of the object.
	 */
	public DataObjectEntity(String path, InputStream inputStream, long size, String contentType) {
		this.path = path;
		this.inputStream = inputStream;
		this.size = size;
		this.contentType = contentType;
	}

	public String getPath() {
		return path;
	}

	public InputStream getInputStream() {
		return inputStream;
	}
	
	public long getSize() {
		return size;
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder();
		toString.append("DataObjectEntity").append('{');
		toString.append("path=").append(path).append(", ");
		toString.append("size=").append(size).append('}');
		return toString.toString();
	}

}
