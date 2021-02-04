/**
 * @author tanmay naik
 *
 */
package com.m3bi.hotel.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "user_details")
public class UserDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String fName;
	private String lName;
	@Column(unique = true)
	private String email;
	private long mobileNumber;
	private int bonusPoints;

	@OneToMany(mappedBy = "user")
	List<RoomEntity> roomList;

	public UserDetailsEntity(long id, String fName, String lName, String email, long mobileNumber, int bonusPoints) {
		super();
		this.id = id;
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.bonusPoints = bonusPoints;
	}

	public UserDetailsEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public int getBonusPoints() {
		return bonusPoints;
	}

	public void setBonusPoints(int bonusPoints) {
		this.bonusPoints = bonusPoints;
	}

	public List<RoomEntity> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<RoomEntity> roomList) {
		this.roomList = roomList;
	}

}
