package com.infotel.mytransfer.controller;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infotel.mytransfer.dto.UserDTO;
import com.infotel.mytransfer.mapper.UserMapper;
import com.infotel.mytransfer.service.DataObjectService;
import com.infotel.mytransfer.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	@Autowired
	private DataObjectService objectService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;
	
	@PostMapping(value = "/create-user")
	public HttpStatus createUser(@RequestBody UserDTO userDto) {
		
		Objects.requireNonNull(userDto);
		
		String uuid = UUID.randomUUID().toString();
		
		userDto.setUuid(uuid);
		
		userService.createUser(userMapper.mapToEntity(userDto));
		
		return objectService.createBucket(uuid) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
	}

	@PostMapping(value = "search-users")
	public List<UserDTO> searchUsers(@RequestBody String filter) {
		return userMapper.mapToDTO(userService.getUsersContaining(filter.toLowerCase()));
	}

}
