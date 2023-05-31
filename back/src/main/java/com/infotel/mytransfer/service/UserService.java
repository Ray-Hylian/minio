package com.infotel.mytransfer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.infotel.mytransfer.dto.UserDTO;
import com.infotel.mytransfer.entities.UserEntity;

@Service
public interface UserService {
	
	/**
	 * Gets all users having a name or email containing the given string.
	 * 
	 * @param filter The string to find a match.
	 * @return The list of users containing the filter.
	 */
	public List<UserEntity> getUsersContaining(String filter);
	
	/**
	 * Creates the user in the database and instantiate a {@link UserEntity}.
	 * 
	 * @param userEntity the DTO containg the user information to create.
	 * @return An instance of {@link UserEntity} containing all informations of the {@link UserDTO}.
	 */
	public UserEntity createUser(UserEntity userEntity);
	
}
