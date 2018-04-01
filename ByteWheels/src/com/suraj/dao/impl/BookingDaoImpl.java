package com.suraj.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.suraj.dao.BookingDao;
import com.suraj.dao.CarsDao;
import com.suraj.model.Booking;
import com.suraj.model.Cars;
import com.suraj.utils.ConnectionFactory;

/**
 * The DAO implementation for Booking Table
 * 
 * @author suraj.udayashankar
 *
 */
public class BookingDaoImpl implements BookingDao {

	public static BookingDaoImpl INSTANCE = null;

	private BookingDaoImpl() {

	}

	public static BookingDaoImpl getInstance() {
		if (INSTANCE == null) {
			synchronized (BookingDaoImpl.class) {
				if (INSTANCE == null) {
					INSTANCE = new BookingDaoImpl();
				}
			}
		}
		return INSTANCE;
	}

	static Logger log = Logger.getLogger(BookingDaoImpl.class);

	/**
	 * This method is to fetch the booking referring to a particular car
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public Booking getBookingByCarId(int id) {
		Booking booking = null;
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement query;
		try {
			query = connection.prepareStatement("select * from `bytewheels`.`booking` where `cars`.`id`=" + id);
			ResultSet rs = query.executeQuery();
			while (rs.next()) {
				booking = new Booking(rs.getInt(1), (Cars) rs.getObject(2), rs.getFloat(3), rs.getBoolean(4),
						rs.getString(5), rs.getDate(6), rs.getDate(7));
			}
		} catch (SQLException e) {
			log.error(e);
		}
		return booking;
	}

	/**
	 * This method is responsible for persisting the booking object into the
	 * Database and return the saved object object outside
	 * 
	 * Returns null if Exception occurs during persisting Returns booking.id = 0
	 * and booking.email = "error field" if simultaneous bookings are made for
	 * the same car on the same date
	 * 
	 * @param booking
	 * @return
	 */
	@Override
	public Booking insertBooking(Booking booking) {
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement query;
		Booking returnObj = null;
		try {
			query = connection.prepareStatement(
					"INSERT INTO `bytewheels`.`booking` (`cars`,`amount`,`paid`,`email`,`fromDate`,`toDate`)VALUES (?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			query.setInt(1, booking.getCar().getId());
			query.setFloat(2, booking.getAmount());
			query.setBoolean(3, booking.isPaid());
			query.setString(4, booking.getEmail());
			query.setDate(5, booking.getFromDate());
			query.setDate(6, booking.getToDate());
			int i = query.executeUpdate();
			if (i == 1) {
				ResultSet rs = query.getGeneratedKeys();
				if (rs != null && rs.next()) {
					returnObj = getBookingById(rs.getInt(1));
					returnObj.setCar(booking.getCar());
					returnObj.setEmail(booking.getEmail());
					returnObj.setAmount(booking.getAmount());
					returnObj.setFromDate(booking.getFromDate());
					returnObj.setToDate(booking.getToDate());
					log.info("inserted into booking, ID : " + returnObj);
				} else {
					log.info("rs is null");
				}
			}

		} catch (SQLException e) {
			log.error(e);
			if (e.getMessage().contains("date_index") || e.getMessage().contains("date_index2")
					&& (e instanceof MySQLIntegrityConstraintViolationException)) {
				returnObj = new Booking(0, null, null, false, "DuplicateEntry", null, null);
			}
		}

		return returnObj;
	}

	/**
	 * This method is to update the payment status of a booking
	 * 
	 * @param booking
	 * @return
	 */

	@Override
	public boolean updatePayment(Booking booking) {
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement query;
		try {
			query = connection.prepareStatement(
					"UPDATE `bytewheels`.`booking` SET `paid`=" + booking.isPaid() + " WHERE `id`=" + booking.getId());
			int i = query.executeUpdate();
			if (i == 1) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method is to fetch the booking for a booking Id
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public Booking getBookingById(int id) {
		Booking booking = null;
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement query;
		try {
			query = connection.prepareStatement("select * from `bytewheels`.`booking` where `id`=" + id);
			ResultSet rs = query.executeQuery();
			while (rs.next()) {
				CarsDao carDao = CarsDaoImpl.getInstance();
				booking = new Booking(rs.getInt(1), carDao.getCarById(rs.getInt(2)), rs.getFloat(3), rs.getBoolean(4),
						rs.getString(5), rs.getDate(6), rs.getDate(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return booking;
	}

}
