package com.m3bi.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.m3bi.hotel.entity.UserDetailsEntity;

/**
 * @author tannaik
 *
 */
public interface UserDetailsEntityRepository extends JpaRepository<UserDetailsEntity, Long> {

	@Query(value = "select u from UserDetailsEntity u where u.email=:emailId")
	public UserDetailsEntity findByEmailId(@Param("emailId") String emailId);

}
