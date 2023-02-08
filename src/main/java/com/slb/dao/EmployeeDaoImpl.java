package com.slb.dao;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.slb.mapper.EmployeeRowMapper;
import com.slb.model.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class EmployeeDaoImpl implements EmployeeDao{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	Employee emp;

	public EmployeeDaoImpl(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	public EmployeeDaoImpl() {
		// TODO Auto-generated constructor stub
	}


	public int createEmpRecord(Employee emp) {
		String SQL="insert into employees(first_name,last_name,salary,address_id) values(?,?,?,?)";
		int rows=jdbcTemplate.update(SQL,emp.firstName,emp.lastName,emp.salary,emp.addressId);
		return rows;
	}
	
	public List<Employee> selectEmpRecords(){
    	String SQL="SELECT emp.emp_id, emp.first_Name,emp.last_name,emp.salary,emp.address_id,ad.address "
                          + "FROM employees emp LEFT JOIN address ad ON emp.address_id =ad.ad_id";
		return jdbcTemplate.query(SQL, new EmployeeRowMapper());
	}
	
	public Employee selectEmp(int empId){
		String SQL="SELECT emp.emp_id, emp.first_Name,emp.last_name,emp.salary,emp.address_id,ad.address "
				+ "FROM employees emp LEFT JOIN address ad ON emp.address_id =ad.ad_id where emp_id="+empId;
		emp= jdbcTemplate.queryForObject(SQL,new EmployeeRowMapper() );
		return emp;
	}
	
	public int updateEmp(Employee emp) {
//		if(addressExists(emp.addressId)==0){
//			insertAddressRecord(emp.addressId,emp.address);
//		}
		String SQL="update employees set first_Name = ?,last_name= ?, salary =? ,address_id=? where emp_id = ?;";
	   return jdbcTemplate.update(SQL,emp.firstName,emp.lastName,emp.salary,emp.addressId,emp.id);
	}
	
	public int deleteEmpRecord(int empId) {
		String SQL="delete from employees where emp_id= "+empId;
		return jdbcTemplate.update(SQL);
		
	}
	
	public int addressExists(int addressId) {
		String SQL="select count(*) from address where ad_id="+addressId;
		return jdbcTemplate.queryForObject(SQL,int.class);
	}
	
	public int insertAddressRecord(int addressId,String address) {
		String query="insert into address(ad_id,address) values(?,?)";
		return jdbcTemplate.update(query,addressId,address);
	}
	
	public List<Employee> selectEmpByName(String firstName){
		String SQL="SELECT emp.emp_id, emp.first_Name,emp.last_name,emp.salary,emp.address_id,ad.address "
				+ "FROM employees emp LEFT JOIN address ad ON emp.address_id =ad.ad_id where emp.first_name ~* '"+firstName+"';";
		return jdbcTemplate.query(SQL, new EmployeeRowMapper());
	}
	
}