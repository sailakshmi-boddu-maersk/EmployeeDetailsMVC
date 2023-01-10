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
	EmployeeDaoImpl employeeDaoImpl;
//	public void connectionDb() {
//		empDaoImpl.connectionDb();
//	}

	public void createEmpRecord(Employee emp) {
		employeeDaoImpl.createEmpRecord(emp);
	}
	public List<Employee> selectEmpRecords(){
		List<Employee> emp= new ArrayList<Employee>();
		emp=employeeDaoImpl.selectEmpRecords();
		return emp;
	}
	public Employee selectEmp(int empId) {
		Employee emp=new Employee();
		emp=employeeDaoImpl.selectEmp(empId);
		return emp;
		
	}
	public void updateEmp(Employee emp) {
		if(employeeDaoImpl.addressExists(emp.addressId)==0){
			employeeDaoImpl.insertAddressRecord(emp.addressId,emp.address);
		}
		employeeDaoImpl.updateEmp(emp);
	}
	public void deleteEmpRecord(int empId) {
	    employeeDaoImpl.deleteEmpRecord(empId);
	}
	public int addressExists(int addressId) {
		int isExists=employeeDaoImpl.addressExists(addressId);
		return isExists;
	}
	public void insertAddressRecord(int empId,String address) {
		employeeDaoImpl.insertAddressRecord(empId, address);
	}
	public List<Employee>selectEmpByName(String firstName) {
		List<Employee> emp= new ArrayList<Employee>();
		emp=employeeDaoImpl.selectEmpByName(firstName);
		return emp;
	}
}
