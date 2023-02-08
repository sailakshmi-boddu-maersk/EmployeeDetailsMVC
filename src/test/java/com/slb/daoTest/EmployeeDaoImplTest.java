package com.slb.daoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.slb.config.MyConfig;
import com.slb.dao.EmployeeDaoImpl;
import com.slb.mapper.EmployeeRowMapper;
import com.slb.model.Employee;

@ExtendWith(MockitoExtension.class)
public class EmployeeDaoImplTest {
	



	 @Mock
	 JdbcTemplate jdbcTemplate;
	 
	 @InjectMocks
	 EmployeeDaoImpl empDaoImpl;
	 
	 @Nested
	 class createEmpRecordTest{
		 
		 @Test //to test createEmpRecord method
		 public void createEmpRecordTest1() {
			 String SQL="insert into employees(first_name,last_name,salary,address_id) values(?,?,?,?)";
			 Employee emp=new Employee();
			 emp.setFirstName("employee1");
			 emp.setLastName("xyz");
			 emp.setSalary(10000);
			 emp.setAddressId(101); 
			 
			 when(jdbcTemplate.update(SQL,emp.firstName,emp.lastName,emp.salary,emp.addressId)).thenReturn(1);
			 assertEquals(1,empDaoImpl.createEmpRecord(emp));
			 verify(jdbcTemplate).update(SQL,emp.firstName,emp.lastName,emp.salary,emp.addressId);
		 }
		 
		 @Test //when createEmpRecords method throws exception
	 	public void createEmpRecordsTest2() {
	 		Employee emp=new Employee();
	 		String SQL="insert into employees(first_name,last_name,salary,address_id) values(?,?,?,?)";
//	 		when(empDaoImpl.createEmpRecord(emp)).thenThrow(RuntimeException.class);
	 		when(jdbcTemplate.update(SQL,emp.firstName,emp.lastName,emp.salary,emp.addressId)).thenThrow(RuntimeException.class);
	 	    assertThrows(RuntimeException.class, () -> {
	 	    	empDaoImpl.createEmpRecord(emp);
	 	    });
	 	   verify(jdbcTemplate).update(SQL,emp.firstName,emp.lastName,emp.salary,emp.addressId);
		 }
		 
	 }
	
	 @Nested
	 class selectEmpRecordsTest{
		 
		 @Test //to test selectEmpRecords method
		 public void selectEmpRecordsTest1() {
//			 String SQL="SELECT emp.emp_id, emp.first_Name,emp.last_name,emp.salary,emp.address_id,ad.address "
//	                 + "FROM employees emp LEFT JOIN address ad ON emp.address_id =ad.ad_id";
			 List<Employee> empList=new ArrayList<Employee>();
			 Employee emp=new Employee();
			 emp.setId(1);
			 emp.setFirstName("employee1");
			 emp.setLastName("xyz");
			 emp.setSalary(10000);
			 emp.setAddressId(101);
			 empList.add(emp);
			 when(jdbcTemplate.query(Mockito.anyString(),Mockito.any(EmployeeRowMapper.class))).thenReturn(empList);
			 assertEquals(empList,empDaoImpl.selectEmpRecords());
			 verify(jdbcTemplate).query(Mockito.anyString(),Mockito.any(EmployeeRowMapper.class));
		 }
		 
		 @Test //when createEmpRecords method throws exception
		 public void selectEmpRecordsTest2() {
//		 	 when(empDaoImpl.selectEmpRecords()).thenThrow(RuntimeException.class);
		 	 when(jdbcTemplate.query(Mockito.anyString(),Mockito.any(EmployeeRowMapper.class))).thenThrow(RuntimeException.class);
		 	 assertThrows(RuntimeException.class, () -> {
		 	    	empDaoImpl.selectEmpRecords();
		 	    });
		 	verify(jdbcTemplate).query(Mockito.anyString(),Mockito.any(EmployeeRowMapper.class));
			 }
		 
	 }
	 
	 @Nested
	 class selectEmpTest{
		 
		 @Test //To test selectEmp method
		 public void selectEmpTest1() {
	         Employee emp=new Employee();
			 emp.setId(1);
			 emp.setFirstName("employee1");
			 emp.setLastName("xyz");
			 emp.setSalary(10000);
			 emp.setAddressId(101);
			 when(jdbcTemplate.queryForObject(Mockito.anyString(),Mockito.any(EmployeeRowMapper.class))).thenReturn(emp);
			 assertEquals(emp,empDaoImpl.selectEmp(1));
			 verify(jdbcTemplate).queryForObject(Mockito.anyString(),Mockito.any(EmployeeRowMapper.class));
		 }
		 @Test //when selectEmp method throws exception
		 public void selectEmpTest2() {
			 when(jdbcTemplate.queryForObject(Mockito.anyString(),Mockito.any(EmployeeRowMapper.class))).thenThrow(RuntimeException.class);
			 assertThrows(RuntimeException.class, () -> {
		 	    	empDaoImpl.selectEmp(1);
		 	    });
			 verify(jdbcTemplate).queryForObject(Mockito.anyString(),Mockito.any(EmployeeRowMapper.class));
		 }
		 
	 }
	 
	 @Nested
	 class updateEmpTest{
		 
		 @Test //to test updateEmp method
		 public void updateEmpTest1() {
			 Employee emp=new Employee();
			 emp.setId(101);
			 emp.setFirstName("employee1");
			 emp.setLastName("xyz");
			 emp.setSalary(10000);
			 emp.setAddressId(101);
			 String SQL="update employees set first_Name = ?,last_name= ?, salary =? ,address_id=? where emp_id = ?;";
			 when(jdbcTemplate.update(SQL,emp.firstName,emp.lastName,emp.salary,emp.addressId,emp.id)).thenReturn(1);
			 assertEquals(1,empDaoImpl.updateEmp(emp));
			 verify(jdbcTemplate).update(SQL,emp.firstName,emp.lastName,emp.salary,emp.addressId,emp.id);
		 }
		 @Test //when updateEmp method throws exception
		 	public void updateEmpTest2() {
		 		Employee emp=new Employee();
		 		 String SQL="update employees set first_Name = ?,last_name= ?, salary =? ,address_id=? where emp_id = ?;";
				 when(jdbcTemplate.update(SQL,emp.firstName,emp.lastName,emp.salary,emp.addressId,emp.id)).thenThrow(RuntimeException.class);
		 	    assertThrows(RuntimeException.class, () -> {
		 	    	empDaoImpl.updateEmp(emp);
		 	    });
		 	   verify(jdbcTemplate).update(SQL,emp.firstName,emp.lastName,emp.salary,emp.addressId,emp.id);
			 }
		 
	 }
	 
	 @Nested
	 class deleteEmpRecordTest{
		 
		 @Test //to test deleteEmpRecords method
		 public void deleteEmpRecordTest1() {
			 when(jdbcTemplate.update(Mockito.anyString())).thenReturn(1);
			 assertEquals(1,empDaoImpl.deleteEmpRecord(1));
			 verify(jdbcTemplate).update(Mockito.anyString());
		 }
		 
		 @Test //when deleteEmpRecords method throws exception
		 public void deleteEmpRecordTest2() {
		 	 when(jdbcTemplate.update(Mockito.anyString())).thenThrow(RuntimeException.class);
		 	 assertThrows(RuntimeException.class, () -> {
		 	    	empDaoImpl.deleteEmpRecord(1);
		 	    });
		 	 verify(jdbcTemplate).update(Mockito.anyString());
			 }
	 }
	 
	 @Nested
	 class addressExistsTest{
		 
		 @Test //to test addressExists method
		 public void addressExistsTest1() {
			 int addressId=101;
			 String SQL="select count(*) from address where ad_id="+addressId;
			 when(jdbcTemplate.queryForObject(SQL,int.class)).thenReturn(1);
			 assertEquals(1,empDaoImpl.addressExists(101));
			 verify(jdbcTemplate).queryForObject(SQL,int.class);
		 } 
		 @Test //when  addressExists method throws exception
		 public void  addressExistsTest2() {
			 String SQL="select count(*) from address where ad_id="+101;
			 when(jdbcTemplate.queryForObject(SQL,int.class)).thenThrow(RuntimeException.class);
		 	 assertThrows(RuntimeException.class, () -> {
		 	    	empDaoImpl.addressExists(101);
		 	    });
		 	verify(jdbcTemplate).queryForObject(SQL,int.class);
			 }
	 }
	 
	 @Nested
	 class insertAddressRecordTest{
		 
		 @Test //to test insertAddressRecord method
		 public void insertAddressRecordTest1() {
			 String query="insert into address(ad_id,address) values(?,?)";
			 when(jdbcTemplate.update(query,101,"abc")).thenReturn(1);
			 assertEquals(1,empDaoImpl.insertAddressRecord(101, "abc"));
			 verify(jdbcTemplate).update(query,101,"abc");
		 }
		 @Test //when  insertAddressRecord method throws exception
		 public void insertAddressRecordTest2() {
			 String query="insert into address(ad_id,address) values(?,?)";
			 when(jdbcTemplate.update(query,101,"abc")).thenThrow(RuntimeException.class);
		 	 assertThrows(RuntimeException.class, () -> {
		 		empDaoImpl.insertAddressRecord(101, "abc");
		 	    });
		 	 verify(jdbcTemplate).update(query,101,"abc");
			 }
	 }
	 
	 @Nested
	 class selectEmpByNameTest{
		 
		 @Test //to test selectEmpByName method
		 public void selectEmpByNameTest1() {
			 List<Employee> empList=new ArrayList<Employee>();
			 Employee emp=new Employee();
			 emp.setId(1);
			 emp.setFirstName("employee1");
			 emp.setLastName("xyz");
			 emp.setSalary(10000);
			 emp.setAddressId(101);
			 empList.add(emp);
			 when(jdbcTemplate.query(Mockito.anyString(),Mockito.any(EmployeeRowMapper.class))).thenReturn(empList);
			 assertEquals(empList,empDaoImpl.selectEmpByName("employee1"));
			 verify(jdbcTemplate).query(Mockito.anyString(),Mockito.any(EmployeeRowMapper.class));
		 } 
		 @Test //when  selectEmpByName method throws exception
		 public void selectEmpByNameTest2() {
			 when(jdbcTemplate.query(Mockito.anyString(),Mockito.any(EmployeeRowMapper.class))).thenThrow(RuntimeException.class);
		 	 assertThrows(RuntimeException.class, () -> {
		 		empDaoImpl.selectEmpByName("employee1");
		 	    });
		 	verify(jdbcTemplate).query(Mockito.anyString(),Mockito.any(EmployeeRowMapper.class));
			 }
	 }
	 @AfterEach
	    public void cleanup() {
	    	empDaoImpl=null;
	    	jdbcTemplate=null;
	 }	
}