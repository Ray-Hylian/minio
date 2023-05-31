package com.infotel.mytransfer.entities;

/**
 * Entity representing an object in a bucket.
 * It stores informations on the representable object.
 */
public class BucketObjectInfoEntity implements Entity {
	
	private String name;
	
	public BucketObjectInfoEntity() {}

	public BucketObjectInfoEntity(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
