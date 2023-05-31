package com.infotel.mytransfer.mapper;

import java.util.List;

import com.infotel.mytransfer.dto.DTO;
import com.infotel.mytransfer.entities.Entity;

public interface EntityToDTOMapper<D extends DTO, E extends Entity> {
	
	/**
	 * Maps a DTO to an Entity
	 * @param entity Entity to map to a DTO
	 * @return a DTO encapsulating this entity's info.
	 */
	public D mapToDTO(E entity);
	
	/**
	 * Maps a DTO List to an Entity List
	 * @param entities Entity List to map to a DTO List
	 * @return a DTO List encapsulating entity's info.
	 */
	public default List<D> mapToDTO(List<E> entities){
		return entities.stream().map(this::mapToDTO).toList();
	}

}
