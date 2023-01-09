package com.slb.employee;


public class Employee {
	
	
	int id;
	String firstName;
	String LastName;
	float salary;
	int addressId;
	String address;
	
	public Employee() {
		super();
	}
	public Employee(String firstName, String lastName, float salary, int addressId, String address) {
		super();
		this.firstName = firstName;
		LastName = lastName;
		this.salary = salary;
		this.addressId = addressId;
		this.address = address;
	}
	public Employee(int id, String firstName, String lastName, float salary, int addressId,String address) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.LastName = lastName;
		this.salary = salary;
		this.addressId = addressId;
		this.address=address;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public float getSalary() {
		return salary;
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
