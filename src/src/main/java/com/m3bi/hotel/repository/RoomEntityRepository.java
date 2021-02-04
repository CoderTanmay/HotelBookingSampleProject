package com.m3bi.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.m3bi.hotel.entity.RoomEntity;

/**
 * @author tannaik
 *
 */
public interface RoomEntityRepository extends JpaRepository<RoomEntity,Long> {

}
