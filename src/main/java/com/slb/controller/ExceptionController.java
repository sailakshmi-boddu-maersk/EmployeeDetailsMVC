package com.slb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionController {
	    
	
	    @ExceptionHandler({NoHandlerFoundException.class})
	    @ResponseStatus(value=HttpStatus.NOT_FOUND)
	    public String noHandlerFound(Model model) {
	    	model.addAttribute("msg","No Handler Found Exception");
	    	return "error";
	    }
	    
	    @ExceptionHandler(value= Exception.class)
	    @ResponseStatus(value=HttpStatus.BAD_REQUEST) 
	    public String GenericExceptionHandler(Model model,Exception e) {
	    	model.addAttribute("msg", e);
	    	return "error";
	    }
	    
//		 @ExceptionHandler({NumberFormatException.class})
//	    public String numberFormatException() {
//	    	return "error";
//	    }
}
