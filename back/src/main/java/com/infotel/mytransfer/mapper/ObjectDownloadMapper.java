package com.infotel.mytransfer.mapper;

import org.springframework.stereotype.Component;

import com.infotel.mytransfer.dto.ObjectDownloadResponseDTO;
import com.infotel.mytransfer.entities.ObjectDownloadEntity;

@Component
public class ObjectDownloadMapper implements EntityToDTOMapper<ObjectDownloadResponseDTO, ObjectDownloadEntity> {

	@Override
	public ObjectDownloadResponseDTO mapToDTO(ObjectDownloadEntity entity) {
		return new ObjectDownloadResponseDTO(entity.getUuid());
	}

}
