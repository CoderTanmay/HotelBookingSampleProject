
package com.m3bi.hotel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.m3bi.hotel.entity.HotelEntity;

/**
 * @author tannaik
 *
 */
public interface HotelEntityRepository extends JpaRepository<HotelEntity,Long> {

	

}
