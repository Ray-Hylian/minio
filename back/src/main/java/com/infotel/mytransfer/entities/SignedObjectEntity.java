package com.infotel.mytransfer.entities;

/**
 * Entity representing an object's data, algorithmically signed.
 */
public class SignedObjectEntity implements Entity {

	private byte[] signature;
	private byte[] object;
	private String name;
	private String contentType;

	public SignedObjectEntity() {}

	public SignedObjectEntity(byte[] signature, byte[] object, String name, String contentType) {
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
