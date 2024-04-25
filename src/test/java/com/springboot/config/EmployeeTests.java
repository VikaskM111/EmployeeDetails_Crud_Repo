package com.springboot.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.springboot.config.model.Employee;
import com.springboot.config.repository.EmployeeRepository;
import com.springboot.config.service.EmployeeService;


@SpringBootTest
public class EmployeeTests {
	
	@Autowired
	private EmployeeService employeeService;
	
	@MockBean
	private EmployeeRepository employeeRepo;
	
	
	// +ve test case for getEmployees method
	@Test
    public void getEmployeesTestPositive() throws Exception{
    	when(employeeService.getAllEmployees())
    	.thenReturn(Stream.of(new Employee(1, "Test", "User_1", "user_1@gmail.com"),
    			new Employee(2, "Test", "User_2", "user_2@gmail.com"))
    			.collect(Collectors.toList()));
    	
    	assertEquals(2, employeeService.getAllEmployees().size());
    }
	
	@Test
    public void getEmployeesTestNegative() throws Exception{
    	when(employeeService.getAllEmployees())
    	.thenReturn(Stream.of(new Employee(1, "Test", "User_1", "user_1@gmail.com"),
    			new Employee(2, "Test", "User_2", "user_2@gmail.com"),
    			new Employee(3, "Test", "User_3", "user_3@gmail.com"))
    			.collect(Collectors.toList()));
    	
    	assertEquals(2, employeeService.getAllEmployees().size());
    }
	
	@BeforeEach
	void setup() {
		System.out.println("before setup");
		Optional<Employee> employee = Optional.of(new Employee(1, "abc", "sss", "abc@gmail.com"));
		Mockito.when(employeeRepo.findById((long) 1)).thenReturn(employee);
	}
	
	
	
	@Test
	public void getUserbyEmployeeIdTestNegative()throws Exception {
		String employeeName = "abc1";
//		Employee employee = employeeService.getEmployeeById(1);
//		assertEquals(employeeName, employee.getFirstName());		
		
	}
	
	
	
	@Test
	public void saveEmployeeTest() throws Exception {
		
		Employee employee = new Employee(1, "a", "b", "ab@gmail.com");
		when(employeeService.saveEmployee(employee)).thenReturn(employee);  // save the user, then return the user
		assertEquals(employee, employeeService.saveEmployee(employee));
	}
	
	@Test
	public void getUserbyEmployeeIdTestPositive()throws Exception {
//		String employeeName = "abc";
//		Employee employee = employeeService.getEmployeeById(1);
//		assertEquals(employeeName, employee.getFirstName());	
		//Employee employee = new Employee(1, "a", "b", "ab@gmail.com");
		employeeService.getEmployeeById(1L);
		//Employee employee1 = employeeRepo.findById(1L).get();
		///////Employee employee = employeeService.getEmployeeById(1L);
		///////Assertions.assertThat(employee.getId()).isEqualTo(1L);
		
	}
	
	@Test
	public void updateRecord() throws Exception{
		Employee employee = new Employee(1, "abb", "b", "ab@gmail.com");
		Mockito.when(employeeRepo.findById(employee.getId())).thenReturn(java.util.Optional.ofNullable(employee));
		Mockito.when(employeeRepo.save(employee));
	}
	
	@Test
	public void deleteEmployeeTest() {
		Employee employee = new Employee(1, "a", "b", "ab@gmail.com");
		//when(employeeRepo.getReferenceById(1L)).thenReturn(employee);
		employeeService.deleteEmployee(1);
		employeeService.getEmployeeById(1);
		
		// here the return type is void, so no return type is expected, hence we cannot use assertEquals
		//need to use verify
		//verify(employeeService.deleteEmployee(1), times(1));
	//	verify(employeeRepo,times(1)).delete(employee);
		//verify(employeeService, null).
	}

		
		
}


