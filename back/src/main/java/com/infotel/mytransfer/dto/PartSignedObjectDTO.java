package com.infotel.mytransfer.dto;

public class PartSignedObjectDTO {

	private String type;
	private byte[] data;
	
	public PartSignedObjectDTO(String type, byte[] data) {
		super();
		this.type = type;
		this.data = data;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
