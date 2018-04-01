package com.suraj.model;


/**
 * Cars model POJO class
 * @author suraj.udayashankar
 *
 */
public class Cars {


	private String type;
	private int cost;
	private int id;
	private String name;
	
	
	/**
	 * The Parameterized constructor for Cars model
	 * @param id
	 * @param name
	 * @param type
	 * @param cost
	 */
	public Cars(int id, String name, String type, int cost) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.cost = cost;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cars [id=");
		builder.append(id);
		builder.append(", ");
		if (name != null) {
			builder.append("name=");
			builder.append(name);
			builder.append(", ");
		}
		if (type != null) {
			builder.append("type=");
			builder.append(type);
			builder.append(", ");
		}
		builder.append("cost=");
		builder.append(cost);
		builder.append("]");
		return builder.toString();
	}
}