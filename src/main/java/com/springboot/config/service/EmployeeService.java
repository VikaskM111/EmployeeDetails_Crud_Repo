package com.springboot.config.service;

import java.util.List;
import java.util.Optional;

import com.springboot.config.model.Employee;

public interface EmployeeService {
	
	Employee saveEmployee(Employee employee);
	
	List<Employee> getAllEmployees();
	
	Optional<Employee> getEmployeeById(long id);
	
	//Employee updateEmployee(Employee employee, long id);

	Employee updateEmployee(Employee employee, long id);
	
	void deleteEmployee(long id);

}
