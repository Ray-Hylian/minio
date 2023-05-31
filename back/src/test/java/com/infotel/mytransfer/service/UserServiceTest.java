package com.infotel.mytransfer.service;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.infotel.mytransfer.entities.UserEntity;
import com.infotel.mytransfer.repository.UserRepository;
import com.infotel.mytransfer.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@InjectMocks
	private UserServiceImpl userService;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private UserEntity userEntity;

	@Test
	public void createUserTest() {
		UserEntity user = new UserEntity(1L, UUID.randomUUID().toString(), "Florentin", "florentin.leclercq@infotel.com");
		userService.createUser(user);
	}
}
