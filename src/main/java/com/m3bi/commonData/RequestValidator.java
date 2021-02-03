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
	public static boolean validateHotelIdAndUserEmailId(RequestParam request) {
		try {
			if (request.getEmailId() != null && !request.getEmailId().isEmpty() && request.getHotelId() > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
}
