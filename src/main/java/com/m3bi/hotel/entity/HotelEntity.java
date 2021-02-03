/**
 * @author tanmay naik
 *
 */
package com.m3bi.hotel.entity;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "hotel")
public class HotelEntity {

	public HotelEntity(long id, String name, int mobileNumber, List<RoomEntity> roomList) {
		super();
		this.id = id;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.roomList = roomList;
	}

	public HotelEntity() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	private String name;
	private long mobileNumber;

	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
	List<RoomEntity> roomList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public List<RoomEntity> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<RoomEntity> roomList) {
		this.roomList = roomList;
	}
	
}
