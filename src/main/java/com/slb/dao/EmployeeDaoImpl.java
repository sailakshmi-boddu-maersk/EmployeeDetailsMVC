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
	
	private static Logger log=Logger.getLogger(EmployeeDaoImpl.class);
	
	public void createEmpRecord(Employee emp) {
		log.info("inserting new employee "+emp.getFirstName()+" record");
		String SQL="insert into employees(first_name,last_name,salary,address_id) values(?,?,?,?)";
		jdbcTemplate.update(SQL,emp.firstName,emp.LastName,emp.salary,emp.addressId);
	}
	
	public List<Employee> selectEmpRecords(){
		log.info("Fetching All Employees details from DB");
    	String SQL="SELECT emp.emp_id, emp.first_Name,emp.last_name,emp.salary,emp.address_id,ad.address "
                          + "FROM employees emp LEFT JOIN address ad ON emp.address_id =ad.ad_id";
		return jdbcTemplate.query(SQL, new EmployeeRowMapper());
	}
	
	public Employee selectEmp(int empId){
		log.info("fetching employee '"+empId+"' record");
		String SQL="SELECT emp.emp_id, emp.first_Name,emp.last_name,emp.salary,emp.address_id,ad.address "
				+ "FROM employees emp LEFT JOIN address ad ON emp.address_id =ad.ad_id where emp_id="+empId;
		return jdbcTemplate.queryForObject(SQL,new EmployeeRowMapper() );
	}
	
	public void updateEmp(Employee emp) {
		log.info("updating employee '"+emp.getId()+" '  record");
		if(addressExists(emp.addressId)==0){
			insertAddressRecord(emp.addressId,emp.address);
		}
		String SQL="update employees set first_Name = ?,last_name= ?, salary =? ,address_id=? where emp_id = ?;";
	    jdbcTemplate.update(SQL,emp.firstName,emp.LastName,emp.salary,emp.addressId,emp.id);
	}
	
	public void deleteEmpRecord(int empId) {
		log.info("deleting employee '"+empId+"' record");
		String SQL="delete from employees where emp_id= "+empId;
		jdbcTemplate.update(SQL);
		
	}
	
	public int addressExists(int addressId) {
		log.info("veryfying address existance");
		String SQL="select count(*) from address where ad_id="+addressId;
		return jdbcTemplate.queryForObject(SQL,int.class);
	}
	
	public void insertAddressRecord(int addressId,String address) {
		log.info("inserting address "+addressId+" record");
		String query="insert into address(ad_id,address) values(?,?)";
		jdbcTemplate.update(query,addressId,address);
	}
	
	public List<Employee> selectEmpByName(String firstName){
		log.info("fetching all employee records with name '"+firstName+" '");
		String SQL="SELECT emp.emp_id, emp.first_Name,emp.last_name,emp.salary,emp.address_id,ad.address "
				+ "FROM employees emp LEFT JOIN address ad ON emp.address_id =ad.ad_id where emp.first_name ~* '"+firstName+"';";
		return jdbcTemplate.query(SQL, new EmployeeRowMapper());
	}
	
}