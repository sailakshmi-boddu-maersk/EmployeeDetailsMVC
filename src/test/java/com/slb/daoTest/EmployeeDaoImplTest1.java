package com.slb.daoTest;


import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.slb.config.MyConfig;
import com.slb.dao.EmployeeDaoImpl;
import com.slb.model.Employee;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MyConfig.class)
public class EmployeeDaoImplTest1 {

	 @Autowired
	 JdbcTemplate jdbcTemplate1;
	 
	 @Autowired
	 EmployeeDaoImpl empDaoImpl;
	
	 @Autowired
	 Employee emp;
	 
	 @Test
	 @Disabled
	 public void createEmpRecordTest1() {
		 
		 
		 EmployeeDaoImpl empDaoImpl=new EmployeeDaoImpl(jdbcTemplate1);
		 emp.setFirstName("employee3");
		 emp.setLastName("abc");
		 emp.setSalary(10000);
		 emp.setAddressId(101);
		 assertEquals(1,empDaoImpl.createEmpRecord(emp));
	 }
	 
	 @Test
	 @Disabled
	 public void insertAddressRecordTest() {
		 EmployeeDaoImpl empDaoImpl=new EmployeeDaoImpl(jdbcTemplate1);
		 assertEquals(1,empDaoImpl.insertAddressRecord(1049,"address2"));
	 }
	 
	 @Test
	 @Disabled
	 public void deleteEmpRecordTest() {
		 EmployeeDaoImpl empDaoImpl=new EmployeeDaoImpl(jdbcTemplate1);
		 assertEquals(1,empDaoImpl.deleteEmpRecord(60));
	 }
	 


}
