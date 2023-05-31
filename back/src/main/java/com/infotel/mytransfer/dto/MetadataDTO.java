package com.infotel.mytransfer.dto;

/**
 * Data Transfer Object containing key and value of a metadata.
 */
public class MetadataDTO implements DTO {
	
	private String key;
	private String value;
	
	public MetadataDTO() {}
	
	public MetadataDTO(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

}
