package com.slb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.slb.model.Employee;
import com.slb.service.EmployeeServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class EmployeeController {
	
	  @Autowired
	  EmployeeServiceImpl employeeServiceImpl;
	  
	  
//	  @Autowired
	  public EmployeeController(EmployeeServiceImpl employeeServiceImpl) {
		super();
		this.employeeServiceImpl = employeeServiceImpl;
	}

	@RequestMapping("/")
	  public String indexPage() {
		  return "index";
	  }
	  
	  @RequestMapping("/home")
	  public String homePage() {
		  return "home-page";
	  }
	  
	  @RequestMapping("/login")
	  public String login() {
		  return "home-page";
	  }
	  
	  @RequestMapping("/new")
	  public String newForm() {
		  return "employee-form";
	  }
	  
	  @RequestMapping("/insert")
	  public String insert(Employee employee,HttpServletRequest request) {
		  employeeServiceImpl.createEmpRecord(employee);
		  return "redirect:/list";
	  }
	  
	  @RequestMapping("/edit")
	  public ModelAndView edit(@RequestParam("id") int id) {
		  ModelAndView mv=new ModelAndView();
		  Employee emp=new Employee();
		  emp=employeeServiceImpl.selectEmp(id);
		  mv.addObject("emp",emp);
		  mv.setViewName("employee-form");
		  return mv;
	  }
	  
	  @RequestMapping("/update")
	  public String update(Employee employee) {
		  employeeServiceImpl.updateEmp(employee);
		  return "redirect:/list";      //sendRedirect
	  }
	  
	  @RequestMapping("/delete")
	  public RedirectView delete(@RequestParam("id") int id,HttpServletRequest request) {
		  employeeServiceImpl.deleteEmpRecord(id);
		  RedirectView redirectView=new RedirectView();
		  redirectView.setUrl(request.getContextPath()+"/list");
		  return redirectView;
	  } 
	  
	  @RequestMapping("/list")
	  public String list(Model model) {
		  List<Employee> empList=new ArrayList<Employee>();
		  empList=employeeServiceImpl.selectEmpRecords();
		  model.addAttribute("listEmployee",empList);
		  return "employee-list";
	  }
	  
	  @RequestMapping("/empRec")
	  public ModelAndView empRec(@RequestParam("action")String action) {
		  
		  ModelAndView mv=new ModelAndView();
		  mv.addObject("actionEditDelete",action);
		  mv.setViewName("employeeName");
		  return mv;  
	  }
	  
	  @RequestMapping("/getEmps")
	  public ModelAndView getEmpByName(@RequestParam("firstName") String firstName,
			           @RequestParam("action")String action) {
		  ModelAndView mv=new ModelAndView();
		  List<Employee> empList=new ArrayList<Employee>();
		  empList=employeeServiceImpl.selectEmpByName(firstName);
		  if(empList.isEmpty()) {
			  mv.addObject("actionEditDelete",action);
			  mv.addObject("msg","Error,unable to find employee details!! "+"Please provide correct employee name..");
			  mv.setViewName("employeeName");
			  return mv;
		  }
		  else {
			  mv.addObject("actionEditDelete",action);
			  mv.addObject("listEmployeeByName",empList);
			  mv.setViewName("employeeListByName");
			  return mv;
		  }  
	  }	 
}
