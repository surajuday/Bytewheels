package com.suraj.dao;

import com.suraj.model.Booking;

/**
 * DAO interface for Booking Table
 * @author suraj.udayashankar
 *
 */
public interface BookingDao {

	Booking insertBooking(Booking booking);

	Booking getBookingByCarId(int id);

	Booking getBookingById(int id);

	boolean updatePayment(Booking booking);

}
