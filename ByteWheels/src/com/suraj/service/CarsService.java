package com.suraj.service;

import java.util.*;

import org.apache.log4j.Logger;

import com.suraj.dao.CarsDao;
import com.suraj.dao.impl.CarsDaoImpl;
import com.suraj.model.Cars;

/**
 * The service class for Cars related services
 * 
 * @author suraj.udayashankar
 *
 */
public class CarsService {

	static Logger log = Logger.getLogger(CarsService.class);

	/**
	 * This method retrieves all the available cars for the given Dates and
	 * groups them by type (optional) and name (optional)
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param ?type
	 * @param ?name
	 * @return
	 */
	public Set<Cars> getCars(String fromDate, String toDate, String type, String name) {
		CarsDao carsDao = CarsDaoImpl.getInstance();
		Set<Cars> cars = carsDao.getCarsByDate(fromDate, toDate, type, name);
		return cars;
	}
/**
 * This method returns a particular Car by passing the primary key (ID)
 * @param id
 * @return
 */
	public Cars getCarsById(String id) {
		CarsDao carsDao = CarsDaoImpl.getInstance();
		Cars cars = carsDao.getCarById(Integer.parseInt(id));
		return cars;
	}

}
