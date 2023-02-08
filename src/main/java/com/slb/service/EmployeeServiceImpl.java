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
	
	public int createEmpRecord(Employee emp) {
		if(employeeDaoImpl.addressExists(emp.addressId)==0){
			employeeDaoImpl.insertAddressRecord(emp.addressId,emp.address);
		}
		return employeeDaoImpl.createEmpRecord(emp);
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
	public int updateEmp(Employee emp) {
		if(employeeDaoImpl.addressExists(emp.addressId)==0){
			employeeDaoImpl.insertAddressRecord(emp.addressId,emp.address);
		}
		return employeeDaoImpl.updateEmp(emp);
	}
	public int deleteEmpRecord(int empId) {
	    return employeeDaoImpl.deleteEmpRecord(empId);
	}
//	public int addressExists(int addressId) {
//		int isExists=employeeDaoImpl.addressExists(addressId);
//		return isExists;
//	}
//	public int insertAddressRecord(int empId,String address) {
//		return employeeDaoImpl.insertAddressRecord(empId, address);
//	}
	public List<Employee>selectEmpByName(String firstName) {
		List<Employee> emp= new ArrayList<Employee>();
		emp=employeeDaoImpl.selectEmpByName(firstName);
		return emp;
	}
}
