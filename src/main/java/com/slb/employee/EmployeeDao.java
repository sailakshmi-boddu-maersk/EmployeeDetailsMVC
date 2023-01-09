package com.slb.employee;

import java.util.List;



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
