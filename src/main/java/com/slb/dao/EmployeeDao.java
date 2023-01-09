package com.slb.dao;

import java.util.List;

import com.slb.model.Employee;



public interface EmployeeDao {
//	void connectionDb();
	void createEmpRecord(Employee emp);
	List<Employee> selectEmpRecords();
	Employee selectEmp(int empId);
	void updateEmp(Employee emp);
	void deleteEmpRecord(int empId);
	int addressExists(int addressId);
	void insertAddressRecord(int empId,String address);
	public List<Employee>selectEmpByName(String firstName);
}
