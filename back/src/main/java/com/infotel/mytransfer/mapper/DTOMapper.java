package com.infotel.mytransfer.mapper;

import com.infotel.mytransfer.dto.DTO;
import com.infotel.mytransfer.entities.Entity;

public interface DTOMapper<D extends DTO, E extends Entity> extends DTOToEntityMapper<D, E>, EntityToDTOMapper<D, E> {
	
}
