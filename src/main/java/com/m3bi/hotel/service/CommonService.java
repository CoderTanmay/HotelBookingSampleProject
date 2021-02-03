package com.m3bi.hotel.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.m3bi.commonData.RequestParam;
import com.m3bi.hotel.entity.HotelEntity;
import com.m3bi.hotel.entity.RoomEntity;
import com.m3bi.hotel.entity.UserDetailsEntity;
import com.m3bi.hotel.repository.HotelEntityRepository;
import com.m3bi.hotel.repository.RoomEntityRepository;
import com.m3bi.hotel.repository.UserDetailsEntityRepository;
import com.m3bi.hotel.util.Constants;

/**
 * @author tannaik
 *
 */
@Service
public class CommonService {

	@Value("${room.price}")
	private int price;
	@Autowired
	HotelEntityRepository hotelEntityRepository;

	@Autowired
	RoomEntityRepository roomEntityRepository;

	@Autowired
	UserDetailsEntityRepository userDetailsEntityRepository;

	/**
	 * @param requestParam
	 * @return ResponseEntity object
	 * @throws Exception
	 */
	public ResponseEntity makeReservation(RequestParam requestParam) throws Exception {
		try {

			Optional<HotelEntity> opt = hotelEntityRepository.findById(requestParam.getHotelId());
			UserDetailsEntity user = userDetailsEntityRepository.findByEmailId(requestParam.getEmailId());
			// Check if user and hotel is present in db.
			// Else return bad request
			if (opt.isPresent() && user != null) {
				HotelEntity hotel = opt.get();
				List<RoomEntity> roomList = hotel.getRoomList().stream()
						.filter(r -> r.getStatus().equalsIgnoreCase(Constants.STATUS_AVAILABLE))
						.collect(Collectors.toList());
				// When rooms Are available,proceed.
				// Else return no rooms available message
				if (roomList.size() > 0) {
					RoomEntity room = roomList.get(0);
					// When bonus points are greater than required for booking,set status as BOOKED.
					// Else set status as Pending for approval.
					if (user.getBonusPoints() >= price) {
						user.setBonusPoints(user.getBonusPoints() - price);
						userDetailsEntityRepository.save(user);
						room.setStatus(Constants.STATUS_BOOKED);
						roomEntityRepository.save(room);
						return new ResponseEntity<>(Constants.ROOM_BOOKING_SUCCESS, HttpStatus.OK);
					} else {
						user.setBonusPoints(user.getBonusPoints() - price);
						userDetailsEntityRepository.save(user);
						room.setStatus(Constants.STATUS_PENDING_FOR_APPROVAL);
						roomEntityRepository.save(room);
						return new ResponseEntity<>(Constants.ROOM_BOOKED_WITH_STATUS_PENDING, HttpStatus.OK);
					}
				} else {
					return new ResponseEntity<>(Constants.NO_ROOMS_AVAILABLE, HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<>(Constants.INVALID_REQUEST, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

}
