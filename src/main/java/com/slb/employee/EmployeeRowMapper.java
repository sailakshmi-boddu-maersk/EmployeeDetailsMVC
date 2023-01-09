package com.slb.employee;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmployeeRowMapper implements RowMapper<Employee>{
	public Employee mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Employee emp = new Employee();
		emp.setId(Integer.parseInt(resultSet.getString(1)));
		emp.setFirstName(resultSet.getString(2));
		emp.setLastName(resultSet.getString(3));
		emp.setSalary(Float.parseFloat(resultSet.getString(4)));
		emp.setAddressId(Integer.parseInt(resultSet.getString(5)));
		emp.setAddress(resultSet.getString(6));
		return emp;
	}
  
}
