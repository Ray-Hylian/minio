package com.infotel.mytransfer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.infotel.mytransfer.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	/**
	 * Queries the repository for a user with a name or email containing the filter.
	 * 
	 * @param filter The string to match with.
	 * @return A list of user corresponding to the filter.
	 */
	@Query("SELECT u FROM UserEntity u WHERE u.name LIKE %:filter% OR u.email LIKE %:filter%")
	public List<UserEntity> getUsersContaining(@Param("filter")String filter);
	
}
