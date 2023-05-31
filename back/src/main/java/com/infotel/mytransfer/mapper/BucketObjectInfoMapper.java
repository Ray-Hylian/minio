package com.infotel.mytransfer.mapper;

import org.springframework.stereotype.Component;

import com.infotel.mytransfer.dto.BucketObjectInfoDTO;
import com.infotel.mytransfer.entities.BucketObjectInfoEntity;

@Component
public class BucketObjectInfoMapper implements DTOMapper<BucketObjectInfoDTO, BucketObjectInfoEntity> {

	@Override
	public BucketObjectInfoEntity mapToEntity(BucketObjectInfoDTO dto) {
		BucketObjectInfoEntity entity = new BucketObjectInfoEntity(dto.getName());
		return entity;
	}

	@Override
	public BucketObjectInfoDTO mapToDTO(BucketObjectInfoEntity entity) {
		BucketObjectInfoDTO dto = new BucketObjectInfoDTO(entity.getName());
		return dto;
	}

}
