/**
 * @author tanmay naik
 *
 */
package com.m3bi.commonData;

import java.sql.Date;

/**
 * @author tannaik
 *
 */
public class RequestParam {

	private String emailId;
	private long hotelId;

	private int bonusPoints;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public long getHotelId() {
		return hotelId;
	}

	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}

	public int getBonusPoints() {
		return bonusPoints;
	}

	public void setBonusPoints(int bonusPoints) {
		this.bonusPoints = bonusPoints;
	}

	
}
