package com.infotel.mytransfer.mapper;

import java.util.List;

import com.infotel.mytransfer.dto.DTO;
import com.infotel.mytransfer.entities.Entity;

public interface DTOToEntityMapper<D extends DTO, E extends Entity> {
	
	/**
	 * Maps an Entity to a DTO
	 * @param dto DTO to map to an Entity
	 * @return an Entity encapsulating this dto's info.
	 */
	public E mapToEntity(D dto);
	
	/**
	 * Maps an Entity List to a DTO List
	 * @param dtos DTO List to map to an Entity List
	 * @return an Entity List encapsulating dto's info.
	 */
	public default List<E> mapToEntity(List<D> dtos){
		return dtos.stream().map(this::mapToEntity).toList();
	}

}
