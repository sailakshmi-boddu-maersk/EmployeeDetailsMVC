package com.slb.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.slb.config.MyConfig;
import com.slb.dao.EmployeeDaoImpl;
import com.slb.model.Employee;
import com.slb.service.EmployeeService;
import com.slb.service.EmployeeServiceImpl;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = MyConfig.class)
public class EmployeeServiceImplTest {

	@Mock 
	EmployeeDaoImpl employeeDaoImpl;
	
	@InjectMocks
	EmployeeServiceImpl employeeServiceImpl;
	
    @Nested
    class createEmpRecordTest{
    	@Test //to test when if block executes - address doesn't exists
    	public void createEmpRecordTest1() {
    		Employee emp=new Employee(1,"Employee1","abc",20000.0F,101,"address1");
    		when(employeeDaoImpl.addressExists(emp.addressId)).thenReturn(0);
    		when(employeeDaoImpl.insertAddressRecord(emp.addressId,emp.address)).thenReturn(1);
    		when(employeeDaoImpl.createEmpRecord(emp)).thenReturn(1);
    		assertEquals(1,employeeServiceImpl.createEmpRecord(emp));
    		verify(employeeDaoImpl).addressExists(emp.addressId);
    		verify(employeeDaoImpl).insertAddressRecord(emp.addressId,emp.address);
    		verify(employeeDaoImpl).createEmpRecord(emp);
    		
    	}
    	
    	@Test //to test when if block not executes - address already exists
    	public void createEmpRecordTest2() {
    		Employee emp=new Employee(1,"Employee1","abc",20000.0F,101,"address1");
    		when(employeeDaoImpl.addressExists(emp.addressId)).thenReturn(1);
    		when(employeeDaoImpl.createEmpRecord(emp)).thenReturn(1);
    		assertEquals(1,employeeServiceImpl.createEmpRecord(emp));
    		verify(employeeDaoImpl).addressExists(emp.addressId);
    		verify(employeeDaoImpl).createEmpRecord(emp);
    	}
    	
    	@Test //when createEmpRecords method throws exception
    	public void createEmpRecordsTest3() {
    		Employee emp=new Employee();
    		when(employeeDaoImpl.createEmpRecord(emp)).thenThrow(RuntimeException.class);
    	    assertThrows(RuntimeException.class, () -> {
    	    	employeeServiceImpl.createEmpRecord(emp);
    	    });
    	    verify(employeeDaoImpl).createEmpRecord(emp);
    	}
    } 
    
    @Nested
    class selectEmpRecords{
    	
    	 @Test //to test selectEmpRecords method
    	    public void selectEmpRecordsTest1() {
    	    	List<Employee> empList=new ArrayList<>();
    			empList.add(new Employee(1,"Employee1","abc",20000.0F,101,"address1"));
    			empList.add(new Employee(2,"Employee1","xyz",20000.0F,101,"address2"));
    			when(employeeDaoImpl.selectEmpRecords()).thenReturn(empList);
    			assertEquals(empList,employeeServiceImpl.selectEmpRecords());
    			verify(employeeDaoImpl).selectEmpRecords();
    	    }
    	    

    		@Test //when selectEmpRecords method throws exception
    		public void selectEmpRecordsTest2() {
    			when(employeeDaoImpl.selectEmpRecords()).thenThrow(RuntimeException.class);
    		    assertThrows(RuntimeException.class, () -> {
    		    	employeeServiceImpl.selectEmpRecords();
    		    });
    		    verify(employeeDaoImpl).selectEmpRecords();
    		}
    }
    
    @Nested
    class selectEmpTest{
    	
    	@Test //to test selectEmp method
    	public void selectEmpTest1() {
    	    Employee emp=new Employee(1,"Employee1","abc",20000.0F,101,"address1");
    	    when(employeeDaoImpl.selectEmp(1)).thenReturn(emp);
    	    assertEquals(emp,employeeServiceImpl.selectEmp(1));
    	    verify(employeeDaoImpl).selectEmp(1);
    	}
    	@Test //when selectEmp method throws exception
 		public void selectEmpTest2() {
 			when(employeeDaoImpl.selectEmp(1)).thenThrow(RuntimeException.class);
 		    assertThrows(RuntimeException.class, () -> {
 		    	employeeServiceImpl.selectEmp(1);
 		    });
 		    verify(employeeDaoImpl).selectEmp(1);
 		}
    }
    
    @Nested //to test updateEmpTest
    class updateEmpTest{
    	@Test //to test when if block executes - address doesn't exists
    	public void updateEmpTest1() {
    		Employee emp=new Employee(1,"Employee1","abc",20000.0F,101,"address1");
    		when(employeeDaoImpl.addressExists(emp.addressId)).thenReturn(0);
    		when(employeeDaoImpl.insertAddressRecord(emp.addressId,emp.address)).thenReturn(1);
    		when(employeeDaoImpl.updateEmp(emp)).thenReturn(1);
    		assertEquals(1,employeeServiceImpl.updateEmp(emp));
    		verify(employeeDaoImpl).addressExists(emp.addressId);
    		verify(employeeDaoImpl).insertAddressRecord(emp.addressId,emp.address);
    		verify(employeeDaoImpl).updateEmp(emp);
    		
    	}
    	
    	@Test //to test when if block not executes - address already exists
    	public void updateEmpTest2() {
    		Employee emp=new Employee(1,"Employee1","abc",20000.0F,101,"address1");
    		when(employeeDaoImpl.addressExists(emp.addressId)).thenReturn(1);
    		when(employeeDaoImpl.updateEmp(emp)).thenReturn(1);
    		assertEquals(1,employeeServiceImpl.updateEmp(emp));
    		verify(employeeDaoImpl).addressExists(emp.addressId);
    		verify(employeeDaoImpl).updateEmp(emp);
    	}
    	
    	@Test //when updateEmp method throws exception
    	public void updateEmpTest3() {
    		Employee emp=new Employee();
    		when(employeeDaoImpl.updateEmp(emp)).thenThrow(RuntimeException.class);
    	    assertThrows(RuntimeException.class, () -> {
    	    	employeeServiceImpl.updateEmp(emp);
    	    });
    	    verify(employeeDaoImpl).updateEmp(emp);
    	}
    	
    }
    
    @Nested
    class deleteEmpRecordTest{
    	 @Test //to test deleteEmpRecords method
    		public void deleteEmpRecordTest() {
    			when(employeeDaoImpl.deleteEmpRecord(1011)).thenReturn(1);
    			assertEquals(1,employeeServiceImpl.deleteEmpRecord(1011));
    			verify(employeeDaoImpl).deleteEmpRecord(1011);
    		}
    	 
    	 @Test //when deleteEmpRecord method throws exception
  		public void deleteEmpRecordTest2() {
  			when(employeeDaoImpl.deleteEmpRecord(1)).thenThrow(RuntimeException.class);
  		    assertThrows(RuntimeException.class, () -> {
  		    	employeeServiceImpl.deleteEmpRecord(1);
  		    });
  		    verify(employeeDaoImpl).deleteEmpRecord(1);
  		}
    }
    
    @Nested
    class selectEmpByNameTest{
    	 @Test //to test selectEmpByName method
    	    public void selectEmpByNameTest1() {
    	    	List<Employee> empList=new ArrayList<>();
    			empList.add(new Employee(1,"Employee1","abc",20000.0F,101,"address1"));
    			empList.add(new Employee(2,"Employee1","xyz",20000.0F,101,"address2"));
    			when(employeeDaoImpl.selectEmpByName("Employee1")).thenReturn(empList);
    			assertEquals(empList,employeeServiceImpl.selectEmpByName("Employee1"));
    			verify(employeeDaoImpl).selectEmpByName("Employee1");
    	    	
    	    }
    	 @Test //when selectEmpByName method throws exception
   		public void selectEmpByNameTest2() {
   			when(employeeDaoImpl.selectEmpByName("abc")).thenThrow(RuntimeException.class);
   		    assertThrows(RuntimeException.class, () -> {
   		    	employeeServiceImpl.selectEmpByName("abc");
   		    });
   		    verify(employeeDaoImpl).selectEmpByName("abc");
        } 
    }
    @AfterEach
    public void cleanup() {
    	employeeServiceImpl=null;
    	employeeDaoImpl=null;
    }
}
