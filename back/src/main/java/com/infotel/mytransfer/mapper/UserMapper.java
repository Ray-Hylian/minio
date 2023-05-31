package com.infotel.mytransfer.mapper;

import org.springframework.stereotype.Component;

import com.infotel.mytransfer.dto.UserDTO;
import com.infotel.mytransfer.entities.UserEntity;

@Component
public class UserMapper implements DTOMapper<UserDTO, UserEntity> {

	@Override
	public UserEntity mapToEntity(UserDTO dto) {
		UserEntity entity = new UserEntity();
		entity.setUuid(dto.getUuid());
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
		return entity;
	}

	@Override
	public UserDTO mapToDTO(UserEntity entity) {
		UserDTO dto = new UserDTO();
		dto.setUuid(entity.getUuid());
		dto.setName(entity.getName());
		dto.setEmail(entity.getEmail());
		return dto;
	}
	
}
