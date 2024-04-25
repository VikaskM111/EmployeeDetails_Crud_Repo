package com.springboot.config.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.config.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	
	Optional<Employee> findByEmail(String email);
}
