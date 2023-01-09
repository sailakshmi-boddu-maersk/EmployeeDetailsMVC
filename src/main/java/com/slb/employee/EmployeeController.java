package com.slb.employee;

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

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class EmployeeController {
	
	  @Autowired
	  EmployeeServiceImpl employeeServiceImpl;
	  
	  @RequestMapping("/login")
	  public String login() {
//		  employeeServiceImpl.connectionDb();
		  return "home-page.jsp";
	  }
	  
	  @RequestMapping("/new")
	  public String newForm() {
		  return "employee-form.jsp";
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
		  mv.setViewName("employee-form.jsp");
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
		  return "employee-list.jsp";
	  }
	  
	  @RequestMapping("/empRec")
	  public ModelAndView empRec(@RequestParam("action")String action) {
		  
		  ModelAndView mv=new ModelAndView();
		  mv.addObject("actionEditDelete",action);
		  mv.setViewName("employeeName.jsp");
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
			  mv.addObject("msg","Error,unable to find employee details!!"+"please provide correct employee name..");
			  mv.setViewName("employeeName.jsp");
			  return mv;
		  }
		  else {
			  mv.addObject("actionEditDelete",action);
			  mv.addObject("listEmployeeByName",empList);
			  mv.setViewName("employeeListByName.jsp");
			  return mv;
		  }  
	  }	 
}
