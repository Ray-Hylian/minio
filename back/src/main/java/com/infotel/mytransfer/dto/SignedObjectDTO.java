package com.infotel.mytransfer.dto;

import com.infotel.mytransfer.entities.SignedObjectEntity;

/**
 * Data Transfer Object of {@link SignedObjectEntity}.
 * Contains all transferable informations of a signed object entity.
 */
public class SignedObjectDTO implements DTO {

	private byte[] signature;
	private byte[] object;
	private String name;
	private String contentType;

	public SignedObjectDTO() {}

	public SignedObjectDTO(byte[] signature, byte[] object, String name, String contentType) {
		this.signature = signature;
		this.object = object;
		this.name = name;
		this.contentType = contentType;
	}

	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	public byte[] getObject() {
		return object;
	}

	public void setObject(byte[] object) {
		this.object = object;
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

}
