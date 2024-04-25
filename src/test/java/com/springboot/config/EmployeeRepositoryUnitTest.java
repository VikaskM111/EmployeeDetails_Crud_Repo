package com.springboot.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.springboot.config.model.Employee;
import com.springboot.config.repository.EmployeeRepository;

@DataJpaTest
public class EmployeeRepositoryUnitTest {
	
	@Autowired
	private EmployeeRepository empRepository;
	
	
	 @Test
     void findAll_should_return_employee_list() {
         // When
         List<Employee> employees = this.empRepository.findAll();
         // Then
         assertEquals(4, employees.size());
     }
	 
	

}
