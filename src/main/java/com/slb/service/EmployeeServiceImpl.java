package com.slb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.slb.dao.EmployeeDaoImpl;
import com.slb.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	EmployeeDaoImpl empDaoImpl;
//	public void connectionDb() {
//		empDaoImpl.connectionDb();
//	}

	public void createEmpRecord(Employee emp) {
		empDaoImpl.createEmpRecord(emp);
	}
	public List<Employee> selectEmpRecords(){
		List<Employee> emp= new ArrayList<Employee>();
		emp=empDaoImpl.selectEmpRecords();
		return emp;
	}
	public Employee selectEmp(int empId) {
		Employee emp=new Employee();
		emp=empDaoImpl.selectEmp(empId);
		return emp;
		
	}
	public void updateEmp(Employee emp) {
		empDaoImpl.updateEmp(emp);
	}
	public void deleteEmpRecord(int empId) {
	    empDaoImpl.deleteEmpRecord(empId);
	}
	public int addressExists(int addressId) {
		int isExists=empDaoImpl.addressExists(addressId);
		return isExists;
	}
	public void insertAddressRecord(int empId,String address) {
		empDaoImpl.insertAddressRecord(empId, address);
	}
	public List<Employee>selectEmpByName(String firstName) {
		List<Employee> emp= new ArrayList<Employee>();
		emp=empDaoImpl.selectEmpByName(firstName);
		return emp;
	}
}
