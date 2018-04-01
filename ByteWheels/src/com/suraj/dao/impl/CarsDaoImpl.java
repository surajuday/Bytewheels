package com.suraj.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.suraj.dao.CarsDao;
import com.suraj.model.Cars;
import com.suraj.utils.ConnectionFactory;

/**
 * The DAO implementation for Cars table
 * 
 * @author suraj.udayashankar
 *
 */
public class CarsDaoImpl implements CarsDao {

	private static CarsDaoImpl INSTANCE = null;

	private CarsDaoImpl() {

	}

	public static CarsDaoImpl getInstance() {
		if (INSTANCE == null) {
			synchronized (CarsDaoImpl.class) {
				if (INSTANCE == null) {
					INSTANCE = new CarsDaoImpl();
				}
			}
		}
		return INSTANCE;

	}

	static Logger log = Logger.getLogger(CarsDaoImpl.class);

	/**
	 * This method fetches all the cars from the Cars table
	 */
	@Override
	public Set<Cars> getAllCars() {
		Set<Cars> cars = new HashSet<Cars>();
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement query;
		try {
			query = connection.prepareStatement("select * from `bytewheels`.`cars`");
			ResultSet rs = query.executeQuery();
			while (rs.next()) {
				Cars car = new Cars(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
				cars.add(car);
			}
		} catch (SQLException e) {
			log.error(e);
		}
		return cars;
	}

	/**
	 * This method returns the cars belonging to a particular type
	 * 
	 * @param type
	 */
	@Override
	public Set<Cars> getCarsByType(String type) {
		Set<Cars> cars = new HashSet<Cars>();
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement query;
		try {
			query = connection.prepareStatement("select * from `bytewheels`.`cars` where `type`=" + type);
			ResultSet rs = query.executeQuery();
			while (rs.next()) {
				Cars car = new Cars(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
				cars.add(car);
			}
		} catch (SQLException e) {
			log.error(e);
		}
		return cars;
	}

	/**
	 * This method is to fetch a particular car by passing the primary key (ID)
	 * 
	 * @param id
	 */
	@Override
	public Cars getCarById(int id) {
		Cars car = null;
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement query;
		try {
			query = connection.prepareStatement("select * from `bytewheels`.`cars` where `id`=" + id);
			ResultSet rs = query.executeQuery();
			while (rs.next()) {
				car = new Cars(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
			}
		} catch (SQLException e) {
			log.error(e);
		}
		return car;
	}

	/**
	 * This method is to return all the available cars between a given fromDate
	 * and toDate, grouping by Type or Name
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param type
	 * @param name
	 * @return
	 */
	@Override
	public Set<Cars> getCarsByDate(String fromDate, String toDate, String type, String name) {
		Set<Cars> cars = new LinkedHashSet<Cars>();
		Connection connection = ConnectionFactory.getConnection();
		try {
			if (null == type) {
				String queryStr = "select type,cost from `bytewheels`.`cars` where `id` not in(select `cars` from `bytewheels`.`booking` where ((`fromDate` BETWEEN '"
						+ fromDate + "' AND '" + toDate + "') OR (`toDate` BETWEEN '" + fromDate + "'AND'" + toDate
						+ "') OR ('" + fromDate + "' BETWEEN `fromDate` AND `toDate`) OR ('" + toDate
						+ "' BETWEEN `fromDate` AND `toDate`)  and (`fromDate` is not null and `toDate` is not null))) group by type";
				log.info("query : " + queryStr);
				PreparedStatement query;
				query = connection.prepareStatement(queryStr);
				ResultSet rs = query.executeQuery();
				while (rs.next()) {
					Cars car = new Cars(0, null, rs.getString(1), rs.getInt(2));
					cars.add(car);
				}
			} else if (null != type && null == name) {
				String queryStr = "select name,COUNT(name),cost from `bytewheels`.`cars` where `id` not in(select `cars` from `bytewheels`.`booking` where ((`fromDate` BETWEEN '"
						+ fromDate + "' AND '" + toDate + "') OR (`toDate` BETWEEN '" + fromDate + "'AND'" + toDate
						+ "') OR ('" + fromDate + "' BETWEEN `fromDate` AND `toDate`) OR ('" + toDate
						+ "' BETWEEN `fromDate` AND `toDate`)  and (`fromDate` is not null and `toDate` is not null))) AND type='"
						+ type + "' group by name";
				log.info("query : " + queryStr);
				PreparedStatement query;
				query = connection.prepareStatement(queryStr);
				ResultSet rs = query.executeQuery();
				while (rs.next()) {
					Cars car = new Cars(rs.getInt(2), rs.getString(1), type, rs.getInt(3));
					cars.add(car);
				}
			} else if (null != type && null != name) {
				String queryStr = "select * from `bytewheels`.`cars` where `id` not in(select `cars` from `bytewheels`.`booking` where ((`fromDate` BETWEEN '"
						+ fromDate + "' AND '" + toDate + "') OR (`toDate` BETWEEN '" + fromDate + "'AND'" + toDate
						+ "') OR ('" + fromDate + "' BETWEEN `fromDate` AND `toDate`) OR ('" + toDate
						+ "' BETWEEN `fromDate` AND `toDate`)  and (`fromDate` is not null and `toDate` is not null))) AND `type`='"
						+ type + "' AND `name`='" + name + "' limit 1";
				log.info("query : " + queryStr);
				PreparedStatement query;
				query = connection.prepareStatement(queryStr);
				ResultSet rs = query.executeQuery();
				rs.next();
				Cars car = new Cars(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
				cars.add(car);
			}

		} catch (SQLException e) {
			log.error(e);
		}
		return cars;
	}

}
