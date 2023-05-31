package com.infotel.mytransfer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotel.mytransfer.entities.UserEntity;
import com.infotel.mytransfer.repository.UserRepository;
import com.infotel.mytransfer.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<UserEntity> getUsersContaining(String filter) {
		return userRepository.getUsersContaining(filter);
	}

	@Override
	public UserEntity createUser(UserEntity user) {
		return userRepository.save(user);
	}

}
