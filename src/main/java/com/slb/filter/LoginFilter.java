package com.slb.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
@WebFilter("/*")
public class LoginFilter extends HttpFilter implements Filter {
       
    public LoginFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
	    HttpServletResponse res=(HttpServletResponse)response;
	    String action = req.getServletPath();
		HttpSession session=req.getSession();
		if(action.equals("/index.jsp") || action.equals("/")) {
			chain.doFilter(request, response);
		}
		else {
	    if(action.equals("/login")) {
	    	String uname=req.getParameter("uname");
			String pass=req.getParameter("password");
			session.setAttribute("uname",uname );
			session.setAttribute("pass",pass);
			if(uname.equals("sai")&& pass.equals("1234")) {
				session.setAttribute("userMsg","");
				chain.doFilter(request, response);
			}
			else {
				session.setAttribute("userMsg","Invalid User!! Enter valid credentials");
                res.sendRedirect("index");
//				dispatcher.forward(req, res);
			}
	    }
	    else if(action.equals("/logout")) {
	    	session.setAttribute("uname","");
	    	session.setAttribute("pass","");
	    	res.sendRedirect("/EmployeeDetailsMVC");
	    }
	    else {
	     String uname="";
	     String pass="";
		 uname=(String) session.getAttribute("uname");
		 pass=(String) session.getAttribute("pass");
		 if(uname==null) {
			 res.sendRedirect("/EmployeeDetailsMVC");
		 }
		 
		 else if(uname.equals("sai") && pass.equals("1234")) {
		chain.doFilter(request, response);
		}
		else 
			res.sendRedirect("/EmployeeDetailsMVC");
	    }
		} 
	}
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
