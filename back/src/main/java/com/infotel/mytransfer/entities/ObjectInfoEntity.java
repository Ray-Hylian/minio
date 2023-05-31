package com.infotel.mytransfer.entities;

import java.time.ZonedDateTime;
import java.util.Map;

/**
 * Entity representing known data on the stored object in a bucket.
 */
public class ObjectInfoEntity implements Entity {

	private String etag;
	private long size;
	private ZonedDateTime lastModified;
	private ZonedDateTime retentionRetainUntilDate;
	private boolean deleteMarker;
	private Map<String, String> userMetadata;
	private String versionId;
	private String contentType;

	public ObjectInfoEntity(String etag, long size, ZonedDateTime lastModified, ZonedDateTime retentionRetainUntilDate,
			boolean deleteMarker, Map<String, String> userMetadata, String versionId, String contentType) {
		this.etag = etag;
		this.size = size;
		this.lastModified = lastModified;
		this.retentionRetainUntilDate = retentionRetainUntilDate;
		this.deleteMarker = deleteMarker;
		this.userMetadata = userMetadata;
		this.versionId = versionId;
		this.contentType = contentType;
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public ZonedDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(ZonedDateTime lastModified) {
		this.lastModified = lastModified;
	}

	public ZonedDateTime getRetentionRetainUntilDate() {
		return retentionRetainUntilDate;
	}

	public void setRetentionRetainUntilDate(ZonedDateTime retentionRetainUntilDate) {
		this.retentionRetainUntilDate = retentionRetainUntilDate;
	}

	public boolean isDeleteMarker() {
		return deleteMarker;
	}

	public void setDeleteMarker(boolean deleteMarker) {
		this.deleteMarker = deleteMarker;
	}

	public Map<String, String> getUserMetadata() {
		return userMetadata;
	}

	public void setUserMetadata(Map<String, String> userMetadata) {
		this.userMetadata = userMetadata;
	}

	public String getVersionId() {
		return versionId;
	}
	
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}
