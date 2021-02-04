/**
 * @author tanmay naik
 *
 */
package com.m3bi.commonData;

public class RequestValidator {

	/**
	 * 
	 * @param request
	 * @return
	 */
	public static boolean validateUserEmailId(RequestParam request) {
		try {
			if (request.getEmailId() != null && !request.getEmailId().isEmpty()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public static boolean validateHotelId(RequestParam request) {
		try {
			if (request.getHotelId() > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public static boolean validateBonusPoints(RequestParam request) {
		try {
			if (request.getBonusPoints() > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
}
