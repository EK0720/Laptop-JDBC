package com.example.demoTest.book;

import java.sql.Date;

public class Laptop {
	private int id;
	private String name;
	private int price;
	private String brand;
	private Date date;
	public Laptop(int id, String name, int price, String brand, Date date, boolean paid) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.brand = brand;
		this.date = date;
		this.paid = paid;
	}
	public int getId() {
		return id;
	}
	public Laptop() {
		super();
		// TODO Auto-generated constructor stub
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	private boolean paid;
}

