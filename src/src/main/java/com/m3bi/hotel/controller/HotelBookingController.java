/**
 * @author tanmay naik
 *
 */
package com.m3bi.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.m3bi.commonData.RequestParam;
import com.m3bi.commonData.RequestValidator;
import com.m3bi.hotel.service.CommonService;

@RestController
public class HotelBookingController {

	@Autowired
	CommonService commonService;

	@PostMapping(value = "/makeReservation")
	public ResponseEntity makeReservation(@RequestBody RequestParam requestParam) {

		try {
			if (RequestValidator.validateHotelId(requestParam) && RequestValidator.validateUserEmailId(requestParam)) {
				return commonService.makeReservation(requestParam);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/addBonusPoints")
	public ResponseEntity addBonusPoints(@RequestBody RequestParam requestParam) {

		try {
			if (RequestValidator.validateBonusPoints(requestParam)
					&& RequestValidator.validateUserEmailId(requestParam)) {
				return commonService.addBonusPoints(requestParam);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
