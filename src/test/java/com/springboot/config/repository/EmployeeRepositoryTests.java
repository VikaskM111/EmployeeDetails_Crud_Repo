package com.springboot.config.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.springboot.config.model.Employee;

@DataJpaTest //tests the persistance/repository layer
public class EmployeeRepositoryTests {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//Junit test for save employee
	@DisplayName("save employee")
//	@Test
	public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
		//given - precondition or setup
		Employee employee = Employee.builder()
				   .firstName("vikas")
				   .lastName("kumar")
				   .email("vikas@gmail.com")
				   .build();		
		//when - action or behavior that we are going to test
		Employee savedEmployee = employeeRepository.save(employee);
				
		//then - verify the output
		assertThat(savedEmployee).isNotNull();
		assertThat(savedEmployee.getId()).isGreaterThan(0);
		
	}
	
	//Junit test for get all employee
	@DisplayName("get all employee")
//	@Test
	public void givenEmployeesList_whenFindAll_thenEmployeeList() {
		//given - precondition or setup
		Employee employee = Employee.builder()
				   .firstName("vikas")
				   .lastName("kumar")
				   .email("vikas@gmail.com")
				   .build();
		Employee employee1 = Employee.builder()
				   .firstName("Luka")
				   .lastName("modric")
				   .email("luka@gmail.com")
				   .build();
		
		employeeRepository.save(employee);
		employeeRepository.save(employee1);
		
		//when - action or behavior that we are going to test
		
		List<Employee> employeeList = employeeRepository.findAll();
		
		//then - verify the output
		assertThat(employeeList).isNotNull();
		assertThat(employeeList.size()).isEqualTo(2);
	}
	
	// Junit test for get employee by Id
	@DisplayName("get employee by Id")
//	@Test
	
	public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
		//given - precondition or setup
		Employee employee = Employee.builder()
				   .firstName("vikas")
				   .lastName("kumar")
				   .email("vikas@gmail.com")
				   .build();
		
	    employeeRepository.save(employee);
	    
	  //when - action or behavior that we are going to test
	    
	    Employee employeeById = employeeRepository.findById(employee.getId()).get();
	    
	  //then - verify the output
	    
	    assertThat(employeeById).isNotNull();
	    
		
	}
	
	// Junit test for update employee
	
	@DisplayName("update employee")
//	@Test
	public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
		//given - precondition or setup
		
		Employee employee = Employee.builder()
				   .firstName("vikas")
				   .lastName("kumar")
				   .email("vikas@gmail.com")
				   .build();
		
	    employeeRepository.save(employee);
		
		//when - action or behavior that we are going to test
	    
	    Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
	    savedEmployee.setEmail("kumar@gmail.com");
	    Employee updatedEmployee =  employeeRepository.save(savedEmployee);
		
		//then - verify the output
	    assertThat(updatedEmployee.getEmail()).isEqualTo("kumar@gmail.com");
	}
	
	// Junit test for delete employee
	@DisplayName("delete employee")
	@Test
	public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {
		
		//given - precondition or setup
		
		Employee employee = Employee.builder()
				   .firstName("vikas")
				   .lastName("kumar")
				   .email("vikas@gmail.com")
				   .build();
		
	    employeeRepository.save(employee);
		
		//when - action or behavior that we are going to test
		employeeRepository.deleteById(employee.getId());
		Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
		
		//then - verify the output
		assertThat(employeeOptional).isEmpty();
		
		
	}

}
