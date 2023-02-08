package com.slb.dao;

import java.util.List;

import com.slb.model.Employee;



public interface EmployeeDao {
//	void connectionDb();
	int createEmpRecord(Employee emp);
	List<Employee> selectEmpRecords();
	Employee selectEmp(int empId);
	int updateEmp(Employee emp);
	int deleteEmpRecord(int empId);
	int addressExists(int addressId);
	int insertAddressRecord(int empId,String address);
	public List<Employee>selectEmpByName(String firstName);
}
