package com.infotel.mytransfer.dto;

import com.infotel.mytransfer.entities.UserEntity;

/**
 * Data Transfer Object of {@link UserEntity}.
 * Contains all transferable informations of a user entity.
 */
public class UserDTO implements DTO {

	private String uuid = null;
	private String name;
	private String email;

	public UserDTO() {}
	
	public UserDTO(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	public UserDTO(String uuid, String name, String email) {
		this.uuid = uuid;
		this.name = name;
		this.email = email;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
