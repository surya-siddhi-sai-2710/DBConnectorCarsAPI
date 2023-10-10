package com.dh.rest.api.model;

public class Cars {

	// Model Parameters
	private int modelno;
	private String cname;
	private int price;

	// default constructor
	public Cars() {

	}

	// constructor with fields
	public Cars(int modelno, String cname, int price) {

		super();
		this.modelno = modelno;
		this.cname = cname;
		this.price = price;
	}

	public int getModelno() {
		return modelno;
	}

	public void setModelno(int modelno) {
		this.modelno = modelno;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Cars [modelno=" + modelno + ", cname=" + cname + ", price=" + price + "]";
	}

}
