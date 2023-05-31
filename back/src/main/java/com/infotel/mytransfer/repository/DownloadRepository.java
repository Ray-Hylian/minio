package com.infotel.mytransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.infotel.mytransfer.entities.ObjectDownloadEntity;


@Repository
public interface DownloadRepository extends JpaRepository<ObjectDownloadEntity, Long> {

	/**
	 * This query is used to find a {@link ObjectDownloadEntity} 
	 * @param uuid for the object download request
	 * @return an object {@link ObjectDownloadEntity}
	 */
	public ObjectDownloadEntity findDistinctByUuid(@Param("uuid") String uuid);
	
	
}
