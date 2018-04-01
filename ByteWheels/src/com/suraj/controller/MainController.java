package com.suraj.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.suraj.model.Booking;
import com.suraj.model.Cars;
import com.suraj.service.BookingService;
import com.suraj.service.CarsService;
import com.suraj.utils.Mailer;

/**
 * The Main Controller that handles all the requests from the UI
 * 
 * @author suraj.udayashankar
 *
 */
@Path("/api")
public class MainController {
	private static final String dateSplitter = "/";
	static Logger log = Logger.getLogger(MainController.class);

	/**
	 * This Gets all the cars list to be displayed on the UI based on their
	 * availability for the given Dates and their Type/Name
	 * 
	 * @param from
	 * @param to
	 * @param type
	 * @param name
	 * @return
	 */
	@Path("/getCars")
	@GET
	public Response getCars(@QueryParam("from") String from, @QueryParam("to") String to,
			@QueryParam("type") String type, @QueryParam("name") String name) {
		log.info("Fetching available cars between " + from + " and " + to);
		CarsService carsService = new CarsService();
		String fromDate = dateFormatter(from);
		String toDate = dateFormatter(to);
		Set<Cars> cars = carsService.getCars(fromDate, toDate, type, name);
		Map<String, Integer> carCount = new HashMap<>();
		for (Cars car : cars) {
			if (carCount.containsKey(car.getName())) {
				int count = carCount.get(car.getName());
				carCount.put(car.getName(), count++);
			} else {
				carCount.put(car.getName(), 1);
			}
		}

		JSONObject json = new JSONObject();
		json.put("count", cars.size());
		json.put("cars", cars);
		log.info(json.toString());
		return Response.status(200).entity(json.toString()).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();

	}

	/**
	 * This method is responsible for making the booking for a particular car by
	 * passing the Dates, Id of the Car, and Email
	 * 
	 * @param from
	 * @param to
	 * @param carId
	 * @param email
	 * @return
	 */
	@Path("/bookCar")
	@GET
	public Response bookCars(@QueryParam("from") String from, @QueryParam("to") String to, @QueryParam("id") int carId,
			@QueryParam("email") String email) {
		BookingService bookingService = new BookingService();
		String fromDate = dateFormatter2(from);
		String toDate = dateFormatter2(to);
		JSONObject json = new JSONObject();
		Booking booking = bookingService.bookCars(fromDate, toDate, carId, email);
		if (null != booking) {
			if (booking.getId() != 0 && booking.getEmail().contains("@")) {
				json.put("id", booking.getId());
				String msg = "Your booking has been successfully confirmed for " + booking.getCar().getName() + " from "
						+ booking.getFromDate() + " to " + booking.getToDate() + "\n" + "booking Id is : "
						+ booking.getId() + " and amount to be paid : " + booking.getAmount();
				json.put("car", new JSONObject(booking.getCar()));
				json.put("booking", new JSONObject(booking));
				Mailer.send("byteTest7986@gmail.com", "suraj7986", email, "Byte-Wheels - Booking Confirmed", msg);
				log.info(json);
			} else {
				json.put("id", 0);
				json.put("error", booking.getEmail());
			}
		} else {
			json.put("id", 0);
		}
		return Response.status(200).entity(json.toString()).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}

	/**
	 * This method sends the captcha email
	 * @param email
	 * @return
	 */
	@Path("/sendEmail")
	@GET
	public Response sendEmail(@QueryParam("emailId") String email) {
		BookingService bookingService = new BookingService();
		String captcha = bookingService.getCaptcha();
		Mailer.send("byteTest7986@gmail.com", "suraj7986", email, "ByteWheels-confirmation", "CAPTCHA : " + captcha);
		JSONObject json = new JSONObject();
		json.put("captcha", captcha);
		return Response.status(200).entity(json.toString()).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT").build();
	}

	
	private String dateFormatter(String date) {
		String[] splits = date.split(dateSplitter);
		String formatedDate = splits[2] + "-0" + (Integer.parseInt(splits[0]) + 1) + "-" + splits[1];
		return formatedDate;
	}

	private String dateFormatter2(String date) {
		String[] splits = date.split(dateSplitter);
		String formatedDate = splits[2] + "/0" + (Integer.parseInt(splits[0]) + 1) + dateSplitter + splits[1];
		return formatedDate;
	}

}
