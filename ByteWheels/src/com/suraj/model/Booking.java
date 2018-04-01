package com.suraj.model;

import java.sql.Date;

/**
 * Booking model POJO class
 * @author suraj.udayashankar
 *
 */
public class Booking {
	
	private int id;
	private Cars car;
	private Float amount;
	private boolean paid;
	private Date fromDate;
	private Date toDate;
	private String email;

	/**
	 * The default constructor for bookinh model 
	 */
	public Booking() {
	}

	/**
	 * The parameterized constructor for booking model
	 * @param id
	 * @param car
	 * @param amount
	 * @param paid
	 * @param email
	 * @param fromDate
	 * @param toDate
	 */
	public Booking(int id, Cars car, Float amount, boolean paid, String email, Date fromDate, Date toDate) {
		this.id = id;
		this.car = car;
		this.amount = amount;
		this.paid = paid;
		this.email = email;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cars getCar() {
		return car;
	}

	public void setCar(Cars car) {
		this.car = car;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Booking [id=");
		builder.append(id);
		builder.append(", ");
		if (car != null) {
			builder.append("car=");
			builder.append(car);
			builder.append(", ");
		}
		if (amount != null) {
			builder.append("amount=");
			builder.append(amount);
			builder.append(", ");
		}
		builder.append("paid=");
		builder.append(paid);
		builder.append(", ");
		if (email != null) {
			builder.append("email=");
			builder.append(email);
			builder.append(", ");
		}
		if (fromDate != null) {
			builder.append("fromDate=");
			builder.append(fromDate);
			builder.append(", ");
		}
		if (toDate != null) {
			builder.append("toDate=");
			builder.append(toDate);
		}
		builder.append("]");
		return builder.toString();
	}

	
}
