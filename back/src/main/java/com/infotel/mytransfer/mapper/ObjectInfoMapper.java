package com.infotel.mytransfer.mapper;

import org.springframework.stereotype.Component;

import com.infotel.mytransfer.dto.ObjectInfoDTO;
import com.infotel.mytransfer.entities.ObjectInfoEntity;

@Component
public class ObjectInfoMapper implements EntityToDTOMapper<ObjectInfoDTO, ObjectInfoEntity> {

	@Override
	public ObjectInfoDTO mapToDTO(ObjectInfoEntity entity) {
		return new ObjectInfoDTO(null, entity.getContentType(), entity.getSize());
	}

}
