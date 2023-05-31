package com.infotel.mytransfer.dto;

public class ObjectInfoDTO implements DTO {

	private String name;
	private String contentType;
	private long size;

	public ObjectInfoDTO(String name, String contentType, long size) {
		this.name = name;
		this.contentType = contentType;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

}
