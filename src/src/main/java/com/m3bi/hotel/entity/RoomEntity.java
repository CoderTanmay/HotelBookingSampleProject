/**
 * @author tanmay naik
 *
 */
package com.m3bi.hotel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "room")
public class RoomEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true)
	private long roomNo;

	@ManyToOne
	@JoinColumn(name = "hotel_id")
	private HotelEntity hotel;

	private String status;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserDetailsEntity user;
	

	public RoomEntity(long id, long roomNo, HotelEntity hotel, String status) {
		super();
		this.id = id;
		this.roomNo = roomNo;
		this.hotel = hotel;
		this.status = status;
	}

	public RoomEntity() {
		super();
	}

	public long getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(long roomNo) {
		this.roomNo = roomNo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public HotelEntity getHotel() {
		return hotel;
	}

	public void setHotel(HotelEntity hotel) {
		this.hotel = hotel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UserDetailsEntity getUser() {
		return user;
	}

	public void setUser(UserDetailsEntity user) {
		this.user = user;
	}

}