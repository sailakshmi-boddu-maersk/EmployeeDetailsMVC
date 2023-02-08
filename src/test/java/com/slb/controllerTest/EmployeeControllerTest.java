package com.slb.controllerTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.slb.config.MyConfig;
import com.slb.controller.EmployeeController;
import com.slb.controller.ExceptionController;
import com.slb.model.Employee;
import com.slb.service.EmployeeService;
import com.slb.service.EmployeeServiceImpl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.ResultActions;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = MyConfig.class)
public class EmployeeControllerTest {
	
	private MockMvc mockMvc;

	 @Mock
	 EmployeeServiceImpl employeeServiceImpl;
	
     @InjectMocks
	 EmployeeController employeeController;
	
	 @BeforeEach
	 public void setUp(){

		 mockMvc = MockMvcBuilders
	                .standaloneSetup(employeeController)
	                .setViewResolvers(viewResolver())
	                .setControllerAdvice(new ExceptionController())
	                .build();
	    }
	
	 @Test
	 public void mockMvcTest() {
		 assertNotNull(mockMvc);
		 assertNotNull(employeeServiceImpl);
	 }
	 
	@Test   //to check index request handling
	public void indexPageTest() throws Exception {
		mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(forwardedUrl("/WEB-INF/views/index.jsp"));
	}
	
	@Test  //to check home request handling
	public void homePageTest() throws Exception {
		mockMvc.perform(get("/home"))
        .andExpect(status().isOk())
        .andExpect(view().name("home-page"))
        .andExpect(forwardedUrl("/WEB-INF/views/home-page.jsp"));
	}
	
	@Test  //to check login request handling
	public void loginTest() throws Exception {
		mockMvc.perform(get("/login"))
        .andExpect(status().isOk())
        .andExpect(view().name("home-page"))
        .andExpect(forwardedUrl("/WEB-INF/views/home-page.jsp"));
	}
	
	@Test  //to check new request handling
	public void newFormTest() throws Exception {
		mockMvc.perform(get("/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("employee-form"))
        .andExpect(forwardedUrl("/WEB-INF/views/employee-form.jsp"));
	}
	
	@Nested  //to check insert request handling 
	class insertTests{
		
		@Test  //when no exception is thrown
		public void insertTest1() throws Exception {
			when(employeeServiceImpl.createEmpRecord(Mockito.any(Employee.class))).thenReturn(1);
			mockMvc.perform(post("/insert")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)		
			.param("id","1")
			.param("firstName","Employee1")
			.param("lastName","abc")
			.param("salary", "20000")
			.param("addressId","101")
			.param("address","address1"))
	        .andExpect(status().isMovedTemporarily())
	        .andExpect(view().name("redirect:/list"))
	        .andExpect(redirectedUrl("/list"));
			verify(employeeServiceImpl).createEmpRecord(Mockito.any(Employee.class));
		}
		
		@Test  //when invalid details where filled in the form
		public void insertTest2() throws Exception {
			mockMvc.perform(post("/insert")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)		
			.param("id","abc")
			.param("firstName","Employee1")
			.param("lastName","abc")
			.param("salary", "20000")
			.param("addressId","101")
			.param("address","address1"))
			.andExpect(status().isBadRequest())
			.andExpect(view().name("error"))
			.andExpect(forwardedUrl("/WEB-INF/views/error.jsp"));
	        
		}
		
		@Test  //while no request parameters are passed
		public void insertTest3() throws Exception {
			mockMvc.perform(post("/insert")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)		
			.param("id","")
			.param("firstName","")
			.param("lastName","")
			.param("salary", "")
			.param("addressId","")
			.param("address",""))
			.andExpect(status().isBadRequest())
			.andExpect(view().name("error"))
			.andExpect(forwardedUrl("/WEB-INF/views/error.jsp"));
	        
		}
		
	}
	
	@Nested   //to check edit request handling 
	class editTests{
		
		@Test     //when request handled without any exception
		public void editTest1() throws Exception {
			Employee emp=new Employee(1,"Employee1","abc",20000.0F,101,"address1");
			when(employeeServiceImpl.selectEmp(1)).thenReturn(emp);
			 mockMvc.perform(post("/edit")
		                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
		                .param("id","1")
		                .sessionAttr("emp",emp))
			           .andExpect(status().isOk())
		               .andExpect(view().name("employee-form"))
		               .andExpect(forwardedUrl("/WEB-INF/views/employee-form.jsp"));
			 
			 verify(employeeServiceImpl).selectEmp(1);		                
		}
		
		@Test  //while passing incorrect form data
		public void editTest2() throws Exception {
			Employee emp=new Employee(1,"Employee1","abc",20000.0F,101,"address1");
			mockMvc.perform(post("/edit")
	                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                .param("id","abc")
	                .sessionAttr("emp",emp))
			        .andExpect(status().isBadRequest())
			        .andExpect(view().name("error"))
			        .andExpect(forwardedUrl("/WEB-INF/views/error.jsp"));
		}
		
		@Test  //while no request parameters are passed
		public void editTest3() throws Exception {
			Employee emp=new Employee(1,"Employee1","abc",20000.0F,101,"address1");
			mockMvc.perform(post("/edit")
	                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                .sessionAttr("emp",emp))
			        .andExpect(status().isBadRequest())
			        .andExpect(view().name("error"))
			        .andExpect(forwardedUrl("/WEB-INF/views/error.jsp"));
		}
	}
	
	
	
	
	@Test   //to check edit request handling 
	public void updateTest() throws Exception {
		when(employeeServiceImpl.updateEmp(Mockito.any(Employee.class))).thenReturn(1);
		mockMvc.perform(post("/update"))
        .andExpect(status().isMovedTemporarily())
        .andExpect(view().name("redirect:/list"))
        .andExpect(redirectedUrl("/list"));
		verify(employeeServiceImpl).updateEmp(Mockito.any(Employee.class));
	}
	
	@Nested  //to check delete request handling 
	class deleteTests{
		@Test   //when request handled without any exception
		public void deleteTest1() throws Exception {
			when(employeeServiceImpl.deleteEmpRecord(1)).thenReturn(1);
			mockMvc.perform(post("/delete")
	                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                .param("id","1"))
					.andExpect(status().isMovedTemporarily())
//					.andExpect(view().name("redirect:/list"))
					.andExpect(redirectedUrl("/list"));
		 
		 verify(employeeServiceImpl).deleteEmpRecord(1);
		}
		
		@Test  //while passing incorrect form data
		public void deleteTest2() throws Exception {
			mockMvc.perform(post("/delete")
	                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                .param("id","abc"))
					.andExpect(status().isBadRequest())
					.andExpect(view().name("error"))
					.andExpect(forwardedUrl("/WEB-INF/views/error.jsp"));
		}
		
		@Test //while no request parameters are passed
		public void deleteTest3() throws Exception {
			mockMvc.perform(post("/delete"))
					.andExpect(status().isBadRequest())
					.andExpect(view().name("error"))
					.andExpect(forwardedUrl("/WEB-INF/views/error.jsp"));
		}
	}
	@Nested //to check empRec request handling 
	class empRecTests{
		@Test  //when request handled without any exception
		public void empRecTest1() throws Exception {
			mockMvc.perform(post("/empRec")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("action", "edit"))
	        .andExpect(status().isOk())
	        .andExpect(view().name("employeeName"))
	        .andExpect(forwardedUrl("/WEB-INF/views/employeeName.jsp"));
		}
		
		@Test  //while passing incorrect form data
		public void empRecTest2() throws Exception {
			mockMvc.perform(post("/empRec")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED))
			.andExpect(status().isBadRequest())
			.andExpect(view().name("error"))
			.andExpect(forwardedUrl("/WEB-INF/views/error.jsp"));
		}
		
		@Test  //while no request parameters are passed
		public void empRecTest3() throws Exception {
			mockMvc.perform(post("/empRec"))
			.andExpect(status().isBadRequest())
			.andExpect(view().name("error"))
			.andExpect(forwardedUrl("/WEB-INF/views/error.jsp"));
		}
	}
	
	
	@Nested  //to check getEmpByName request handling 
	class getEmpByNameTests{
		
		@Test   //when request handled without any exception
		public void getEmpByNameTest1() throws Exception {
			List<Employee> empList=new ArrayList<>();
			Employee emp=new Employee(1,"Employee1","abc",20000.0F,101,"address1");
			empList.add(emp);
			empList.add(new Employee(2,"Employee1","xyz",20000.0F,101,"address2"));
			when(employeeServiceImpl.selectEmpByName("Employee1")).thenReturn(empList);
			mockMvc.perform(get("/getEmps")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.param("firstName", "Employee1")
					.param("action", "edit"))
			        .andExpect(status().isOk())
			        .andExpect(view().name("employeeListByName"))
	                .andExpect(forwardedUrl("/WEB-INF/views/employeeListByName.jsp"))
			        .andExpect(model().attribute("listEmployeeByName", hasSize(2)))
	                .andExpect(model().attribute("listEmployeeByName", hasItem(
	                        allOf(
	                                hasProperty("id", is(1)),
	                                hasProperty("firstName", is("Employee1")),
	                                hasProperty("lastName", is("abc")),
	                                hasProperty("salary", is(20000.0F)),
	                                hasProperty("addressId", is(101)),
	                                hasProperty("address", is("address1"))
	                        )
	                )))
	                .andExpect(model().attribute("listEmployeeByName", hasItem(
	                		allOf(
	                                hasProperty("id", is(2)),
	                                hasProperty("firstName", is("Employee1")),
	                                hasProperty("lastName", is("xyz")),
	                                hasProperty("salary", is(20000.0F)),
	                                hasProperty("addressId", is(101)),
	                                hasProperty("address", is("address2"))
	                        )
	                )));
		}
		
		@Test  //while employee data was not found 
		public void getEmpByNameTest2() throws Exception {
			List<Employee> empList=new ArrayList<>();
			when(employeeServiceImpl.selectEmpByName("Employee1")).thenReturn(empList);
			mockMvc.perform(get("/getEmps")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.param("firstName", "Employee1")
					.param("action", "edit"))
			        .andExpect(status().isOk())
			        .andExpect(model().attribute("msg",is("Error,unable to find employee details!! "+"Please provide correct employee name..")))
			        .andExpect(view().name("employeeName"))
	                .andExpect(forwardedUrl("/WEB-INF/views/employeeName.jsp"));
		}
		
		@Test  //while passing incorrect form data
		public void getEmpByNameTest3() throws Exception {
			List<Employee> empList=new ArrayList<>();
			when(employeeServiceImpl.selectEmpByName("Employee1")).thenReturn(empList);
			mockMvc.perform(get("/getEmps")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.param("firstName", "123")
					.param("action", "edit"))
					.andExpect(status().isBadRequest())
					.andExpect(view().name("error"))
					.andExpect(forwardedUrl("/WEB-INF/views/error.jsp"));
		}
		
		@Test  //while no request parameters are passed
		public void getEmpByNameTest4() throws Exception {
			mockMvc.perform(get("/getEmps")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.param("action", "edit"))
					.andExpect(status().isBadRequest())
					.andExpect(view().name("error"))
					.andExpect(forwardedUrl("/WEB-INF/views/error.jsp"));
		}
		
	}
	
	
	
	
	@Test  //to check getEmpByName request handling 
    public void listTest() throws Exception {
		
		List<Employee> empList=new ArrayList<>();
		Employee emp=new Employee(1,"Employee1","abc",20000.0F,101,"address1");
		empList.add(emp);
		empList.add(new Employee(2,"Employee2","abc",20000.0F,101,"address2"));
        when(employeeServiceImpl.selectEmpRecords()).thenReturn(empList);
        mockMvc.perform(get("/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee-list"))
                .andExpect(forwardedUrl("/WEB-INF/views/employee-list.jsp"))
                .andExpect(model().attribute("listEmployee", hasSize(2)))
                .andExpect(model().attribute("listEmployee", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("firstName", is("Employee1")),
                                hasProperty("lastName", is("abc")),
                                hasProperty("salary", is(20000.0F)),
                                hasProperty("addressId", is(101)),
                                hasProperty("address", is("address1"))
                        )
                )))
                .andExpect(model().attribute("listEmployee", hasItem(
                		allOf(
                                hasProperty("id", is(2)),
                                hasProperty("firstName", is("Employee2")),
                                hasProperty("lastName", is("abc")),
                                hasProperty("salary", is(20000.0F)),
                                hasProperty("addressId", is(101)),
                                hasProperty("address", is("address2"))
                        )
                )));
 
        verify(employeeServiceImpl).selectEmpRecords();
        verifyNoMoreInteractions(employeeServiceImpl);
    }
	
	@Nested
	class listTest{
		
		@Test
		public void listTest1() throws Exception {
			List<Employee> empList=new ArrayList<>();
			Employee emp=new Employee(1,"Employee1","abc",20000.0F,101,"address1");
			empList.add(emp);
			empList.add(new Employee(2,"Employee1","xyz",20000.0F,101,"address2"));
			when(employeeServiceImpl.selectEmpRecords()).thenReturn(empList);
			mockMvc.perform(get("/list"))
			        .andExpect(status().isOk())
			        .andExpect(view().name("employee-list"))
	                .andExpect(forwardedUrl("/WEB-INF/views/employee-list.jsp"))
			        .andExpect(model().attribute("listEmployee", hasSize(2)))
	                .andExpect(model().attribute("listEmployee", hasItem(
	                        allOf(
	                                hasProperty("id", is(1)),
	                                hasProperty("firstName", is("Employee1")),
	                                hasProperty("lastName", is("abc")),
	                                hasProperty("salary", is(20000.0F)),
	                                hasProperty("addressId", is(101)),
	                                hasProperty("address", is("address1"))
	                        )
	                )))
	                .andExpect(model().attribute("listEmployee", hasItem(
	                		allOf(
	                                hasProperty("id", is(2)),
	                                hasProperty("firstName", is("Employee1")),
	                                hasProperty("lastName", is("xyz")),
	                                hasProperty("salary", is(20000.0F)),
	                                hasProperty("addressId", is(101)),
	                                hasProperty("address", is("address2"))
	                        )
	                )));
		}
	}
	
	 @AfterEach
	    public void cleanup() {
		 employeeServiceImpl=null;
		 employeeController=null;
		 mockMvc=null;
	 }

	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolve = new InternalResourceViewResolver();
	     viewResolve.setPrefix("/WEB-INF/views/");
	     viewResolve.setSuffix(".jsp");
	 
	     return viewResolve;
	}

}
