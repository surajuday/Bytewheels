package com.suraj.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.suraj.dao.BookingDao;
import com.suraj.dao.CarsDao;
import com.suraj.dao.impl.BookingDaoImpl;
import com.suraj.dao.impl.CarsDaoImpl;
import com.suraj.model.Booking;
import com.suraj.model.Cars;
import com.suraj.utils.PropertyLoader;

/**
 * The service class for Booking related Services
 * @author suraj.udayashankar
 *
 */
public class BookingService {
	
	static Logger log = Logger.getLogger(BookingService.class);

	/**
	 * This service method is responsible for making a booking of a car for the given Dates.
	 * @param from
	 * @param to
	 * @param id
	 * @param email
	 * @return
	 */
	public Booking bookCars(String from, String to, int id,String email) {
		Booking booking = new Booking();
		CarsDao carsDao = CarsDaoImpl.getInstance();
		Cars car = carsDao.getCarById(id);
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		java.util.Date utilDate_from = null;
		java.util.Date utilDate_to = null;
		try {
			utilDate_from = format.parse(from);
			Date sqlDate_from = new Date(utilDate_from.getTime());
			utilDate_to = format.parse(to);
			Date sqlDate_to = new Date(utilDate_to.getTime());
			booking.setFromDate(sqlDate_from);
			booking.setToDate(sqlDate_to);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		booking.setCar(car);
		booking.setAmount(amountCalculator(utilDate_from,utilDate_to,car.getCost()));
		booking.setPaid(false);
		booking.setEmail(email);
		BookingDao bookingDao = BookingDaoImpl.getInstance();
		return bookingDao.insertBooking(booking);
	}

	private Float amountCalculator(java.util.Date utilDate_from, java.util.Date utilDate_to, int cost) {
		long diffInMillies = utilDate_to.getTime() - utilDate_from.getTime();
		long days=TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		return (float) ((days+1)*cost);
	}
	
	/**
	 * This method is responsible for generating the captcha
	 * @return
	 */
	public String getCaptcha() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < Integer.parseInt(PropertyLoader.getProperty("captcha.length"))) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
