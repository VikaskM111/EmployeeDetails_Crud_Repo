package com.springboot.config.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.config.exception.ResourceNotFoundException;
import com.springboot.config.model.Employee;
import com.springboot.config.repository.EmployeeRepository;
import com.springboot.config.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	//@Autowired
	private EmployeeRepository employeeRepository;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Employee saveEmployee(Employee employee) {	
		 Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
	        if(savedEmployee.isPresent()){
	            throw new ResourceNotFoundException("Employee already exist with given email:" + employee.getEmail());
	        }
	        return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Optional<Employee> getEmployeeById(long id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		if(employee.isPresent()) {
			return Optional.of(employee.get());
		}else {
			System.out.println("inside get_id");
			throw new ResourceNotFoundException("Employee", "Id", id);
		}
	}

	@Override
	public Employee updateEmployee(Employee employee, long id) {
		//first we need to check whether employee with given id is existing in database or not
		Employee existingEmployee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", id));
		
		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setEmail(employee.getEmail());
		
		//save existing  employee to DB
		employeeRepository.save(existingEmployee);
		return existingEmployee;
	}

	@Override
	public void deleteEmployee(long id) {
		employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", id));
		//employeeRepository.deleteById(id);
		
	}



}
