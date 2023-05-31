package com.infotel.mytransfer.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity allowing to create the object_download table on database.
 * It stores information about a download request.
 */
@javax.persistence.Entity
@Table(name = "object_download")
public class ObjectDownloadEntity implements Entity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="uuid")
	private String uuid;
	
	@Column(name="bucket")
	private String bucket;
	
	@Column(name="path")
	private String path;

	public ObjectDownloadEntity(Long id, String uuid, String bucket, String path) {
		this.id = id;
		this.uuid = uuid;
		this.bucket = bucket;
		this.path = path;
	}
	
	public ObjectDownloadEntity(String uuid, String bucket, String path) {
		this.uuid = uuid;
		this.bucket = bucket;
		this.path = path;
	}
	
	public ObjectDownloadEntity() {}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
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
