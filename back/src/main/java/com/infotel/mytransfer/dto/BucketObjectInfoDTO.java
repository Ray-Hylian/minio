package com.infotel.mytransfer.dto;

import com.infotel.mytransfer.entities.BucketObjectInfoEntity;

/**
 * Data Transfer Object of {@link BucketObjectInfoEntity}.
 * Contains all transferable informations of a bucket object info.
 */
public class BucketObjectInfoDTO implements DTO {
	
	private String name;
	
	public BucketObjectInfoDTO() {}

	public BucketObjectInfoDTO(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
