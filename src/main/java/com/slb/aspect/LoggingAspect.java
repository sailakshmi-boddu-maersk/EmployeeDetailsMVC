package com.slb.aspect;

import org.apache.log4j.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.slb.model.Employee;


@Aspect
@Component
public class LoggingAspect {

	private static Logger log=Logger.getLogger(LoggingAspect.class);
	
	@Around("AllMethodsInDaoImpl()")
	public Object myAroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Object returnValue;
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName=joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String MessageString=getPreMessage(className,methodName,args);;
		try {
            log.info("Entering to "+MessageString);
			returnValue=(Object) joinPoint.proceed();
			log.info(MessageString+" executed succesfully!!!");
		    return returnValue;
		}
		catch(Throwable e){
			log.error("implementing "+MessageString+" throws an exception : "+e);
		}
		return null;
	}

	@Pointcut("within(com.slb.dao.EmployeeDaoImpl)")
	public void AllMethodsInDaoImpl() {
		
	}
	
	private static String getPreMessage(String className,String methodName,Object[] args) {
		StringBuilder builder = new StringBuilder().append(className).append(".")
                .append(methodName)
                .append("(");
		if(methodName.equals("updateEmp") || methodName.equals("createEmpRecord")) {
			Employee emp=(Employee) args[0];
			builder.append(emp.id+","+emp.firstName+","+emp.lastName+","+emp.addressId+","+emp.address);
		}
		else {
			for ( int i = 0; i < args.length; i++ ) {
				if ( i != 0 ) {
					builder.append(", ");
				}
				builder.append(args[i]);
			}
		}
		return builder.append(")").toString();
		

	}
}
