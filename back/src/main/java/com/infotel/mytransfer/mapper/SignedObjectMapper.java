package com.infotel.mytransfer.mapper;

import org.springframework.stereotype.Component;

import com.infotel.mytransfer.dto.SignedObjectDTO;
import com.infotel.mytransfer.entities.SignedObjectEntity;

@Component
public class SignedObjectMapper implements DTOMapper<SignedObjectDTO, SignedObjectEntity> {

	@Override
	public SignedObjectEntity mapToEntity(SignedObjectDTO dto) {
		SignedObjectEntity entity = new SignedObjectEntity(dto.getSignature(), dto.getObject(), dto.getName(), dto.getContentType());
		return entity;
	}

	@Override
	public SignedObjectDTO mapToDTO(SignedObjectEntity entity) {
		SignedObjectDTO dto = new SignedObjectDTO(entity.getSignature(), entity.getObject(), entity.getName(), entity.getContentType());
		return dto;
	}

}
