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
				List<RoomEntity> roomList = hotel.getRoomList();
				Optional<RoomEntity> optionalRoom = roomList.stream()
						.filter(r -> r.getStatus().equalsIgnoreCase(Constants.STATUS_AVAILABLE)).findFirst();
				// When rooms Are available,proceed.
				// Else return no rooms available message
				if (optionalRoom.isPresent()) {
					RoomEntity room = optionalRoom.get();
					// When bonus points are greater than required for booking,set status as BOOKED.
					// Else set status as Pending for approval.
					List<RoomEntity> list = user.getRoomList();
					room.setUser(user);
					user.setRoomList(list);
					if (user.getBonusPoints() >= price) {
						user.setBonusPoints(user.getBonusPoints() - price);
						room.setStatus(Constants.STATUS_BOOKED);
						list.add(room);
						userDetailsEntityRepository.save(user);
						// roomEntityRepository.save(room);
						return new ResponseEntity<>(Constants.ROOM_BOOKING_SUCCESS, HttpStatus.OK);
					} else {
						room.setStatus(Constants.STATUS_PENDING_FOR_APPROVAL);
						list.add(room);
						user.setBonusPoints(user.getBonusPoints() - price);
						userDetailsEntityRepository.save(user);
						return new ResponseEntity<>(Constants.ROOM_BOOKED_WITH_STATUS_PENDING, HttpStatus.OK);
					}
				} // Check if user has bonusPoints required for booking.
					// IF yes,then check for any pending bookings and allot them to user
				else if (user.getBonusPoints() >= price) {
					// Check for pending rooms
					return checkForPendingBookings(roomList, user);
				} else {
					return new ResponseEntity<>(Constants.NO_ROOMS_AVAILABLE, HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<>(Constants.DATA_NOT_FOUND, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	public ResponseEntity addBonusPoints(RequestParam requestParam) {
		UserDetailsEntity user = userDetailsEntityRepository.findByEmailId(requestParam.getEmailId());
		// Check if user and hotel is present in db.
		// Else return bad request
		if (user != null) {
			user.setBonusPoints(user.getBonusPoints() + requestParam.getBonusPoints());
			// Check after addition if user has enough points to reverse pending booking
			// status to booked
			if (user.getBonusPoints() > price) {
				return checkForPendingBookingsOfUser(user);
			} else {
				userDetailsEntityRepository.save(user);
				return new ResponseEntity<>(Constants.BONUS_POINTS_ADDED_SUCCESS, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>(Constants.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity checkForPendingBookingsOfUser(UserDetailsEntity user) {
		List<RoomEntity> list = user.getRoomList().stream()
				.filter(r -> r.getStatus().equalsIgnoreCase(Constants.STATUS_PENDING_FOR_APPROVAL))
				.collect(Collectors.toList());
		if (list.size() > 0) {
			// To stop execution of booking rooms for a user when he does not have required
			// bonus points.
			boolean sufficientPoints = true;
			for (int i = 0; i < list.size() && sufficientPoints; i++) {
				// Here we check bonus points everytime as they will be decreasing after each
				// booking.
				if (user.getBonusPoints() > price) {
					updateRoomStatusForUser(list.get(i), user);
				} else {
					sufficientPoints = false;
				}
			}
			return new ResponseEntity<>(Constants.ROOM_BOOKING_SUCCESS, HttpStatus.OK);
		} else {
			userDetailsEntityRepository.save(user);
			return new ResponseEntity<>(Constants.BONUS_POINTS_ADDED_SUCCESS, HttpStatus.OK);
		}
	}

	public ResponseEntity checkForPendingBookings(List<RoomEntity> roomList, UserDetailsEntity user) {
		Optional<RoomEntity> optionalRoom = roomList.stream()
				.filter(r -> r.getStatus().equalsIgnoreCase(Constants.STATUS_PENDING_FOR_APPROVAL)).findFirst();
		if (optionalRoom.isPresent()) {
			if (user.getBonusPoints() > price) {
				updateRoomStatusForUser(optionalRoom.get(), user);
			}
			return new ResponseEntity<>(Constants.ROOM_BOOKING_SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Constants.NO_ROOMS_AVAILABLE, HttpStatus.OK);
		}
	}

	public void updateRoomStatusForUser(RoomEntity room, UserDetailsEntity user) {
		room.setUser(user);
		room.setStatus(Constants.STATUS_BOOKED);
		List<RoomEntity> list = user.getRoomList();
		list.add(room);
		user.setRoomList(list);
		user.setBonusPoints(user.getBonusPoints() - price);
		userDetailsEntityRepository.save(user);
	}
}
