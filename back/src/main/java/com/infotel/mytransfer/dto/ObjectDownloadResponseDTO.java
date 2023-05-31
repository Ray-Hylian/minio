package com.infotel.mytransfer.dto;

import com.infotel.mytransfer.entities.ObjectDownloadEntity;

/**
 * Data Transfer Object of {@link ObjectDownloadEntity}.
 * Contains all transferable informations of a object download response.
 */
public class ObjectDownloadResponseDTO implements DTO {
	
	private String uuid;
	
	public ObjectDownloadResponseDTO(String uuid) {
		this.uuid = uuid;
	}

	public ObjectDownloadResponseDTO() {
	}


	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
