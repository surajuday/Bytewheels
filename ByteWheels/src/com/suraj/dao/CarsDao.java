package com.suraj.dao;

import java.util.Set;

import com.suraj.model.Cars;


/**
 * DAO interface for Cars Table
 * @author suraj.udayashankar
 *
 */
public interface CarsDao {
	
	Set<Cars> getAllCars();
	
	Set<Cars> getCarsByDate(String from,String to,String type,String name);
	
	Set<Cars> getCarsByType(String type);
	
	Cars getCarById(int id);
}
