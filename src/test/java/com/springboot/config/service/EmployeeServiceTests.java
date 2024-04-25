package com.springboot.config.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;

import com.springboot.config.model.Employee;
import com.springboot.config.repository.EmployeeRepository;
import com.springboot.config.service.impl.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)  // we are going to extend our class behaviour from Mockito extension
public class EmployeeServiceTests {
	@Mock
	private EmployeeRepository employeeRepository;
	
	
	//@InjectMock create the mock object of the class and injects the mocks that are marked with the annotations @Mock into it
	@InjectMocks                //injects one mock dependency to the other
	private EmployeeServiceImpl employeeService;
	
	
	private Employee employee;
	
	@BeforeEach
	public void setup() {
		//employeeRepository = Mockito.mock(EmployeeRepository.class);   // mocking a repository class
		//employeeService = new EmployeeServiceImpl(employeeRepository); //injecting repository class to service class 
		 employee = Employee.builder()
				   .id(1L)
				   .firstName("vikas")
				   .lastName("kumar")
				   .email("vikas@gmail.com")
				   .build();
	}
	
	//Junit test for save employee 
	@DisplayName("save employee")
//	@Test
	public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
		//given - precondition or setup
		
		given(employeeRepository.findByEmail(employee.getEmail()))
        .willReturn(Optional.empty());
		given(employeeRepository.save(employee)).willReturn(employee);  //we configured save employee and what this method will return
		//when - action or behavior that we are going to test
		System.out.println(employeeRepository);
		System.out.println(employeeService);
		Employee savedEmployee = employeeService.saveEmployee(employee);
		
		System.out.println(savedEmployee);
		//then - verify the output
		assertThat(savedEmployee).isNotNull();
		assertEquals(employee.getFirstName(), savedEmployee.getFirstName());	
	}
	
	
	//Junit test for save employee which throws exception
		@DisplayName("save employee which throws exception")
//		@Test
		public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {
			//given - precondition or setup
			
			given(employeeRepository.findByEmail(employee.getEmail()))
	        .willReturn(Optional.of(employee));     // so here we are expecting to return an employee object
			given(employeeRepository.save(employee)).willReturn(employee);  //we configured save employee and what this method will return
			
			System.out.println(employeeRepository);
			System.out.println(employeeService);
			
			//when - action or behavior that we are going to test
			Employee savedEmployee = employeeService.saveEmployee(employee);
			
			System.out.println(savedEmployee);
			//then - verify the output
			//assertThat(savedEmployee).isNotNull();
			
			verify(employeeRepository,never()).save(any(Employee.class));
			
		}
		
		//Junit test for getAllEmployees +ve senario
				@DisplayName("getAllEmployees")
	//			@Test
				public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() {
					
					//given - precondition or setup
					
					Employee employee1 = Employee.builder()
							.id(2L)
							.firstName("Cloud")
							.lastName("User")
							.email("cloud@gmail.com")
							.build();
					
					given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));
					
					//when - action or behavior that we are going to test
					
					List<Employee> employeeList = employeeService.getAllEmployees();
					//then - verify the output
					assertThat(employeeList).isNotNull();
					assertThat(employeeList.size()).isEqualTo(2);
				}
				
			//Junit test for getAllEmployees -ve senario
				@DisplayName("getAllEmployees with invalid employee")
		//		@Test
				public void givenInvalidEmployee_whenGetAllEmployees_thenReturnEmptyEmployeesList() {
					
					//given - precondition or setup
					
					Employee employee1 = Employee.builder()
							.id(2L)
							.firstName("Cloud")
							.lastName("User")
							.email("cloud@gmail.com")
							.build();
					
					given(employeeRepository.findAll()).willReturn(Collections.emptyList());
					
					//when - action or behavior that we are going to test
					
					List<Employee> employeeList = employeeService.getAllEmployees();
					//then - verify the output
					assertThat(employeeList).isEmpty();
					assertThat(employeeList.size()).isEqualTo(0);
				}
				
				
				//Junit test for get employee by Id
				@DisplayName("get employee By Id")
	//			@Test
				public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
					
					//given - precondition or setup
					
					
					
					given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
							
					
					//when - action or behavior that we are going to test
					
					Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();
					
				
					//then - verify the output
					assertThat(savedEmployee).isNotNull();
					
				}
				
				 @DisplayName("updateEmployee")
	//			    @Test
				    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
				        // given - precondition or setup
					    given(employeeRepository.findById(employee.getId())) .willReturn(Optional.of(employee));  
				        given(employeeRepository.save(employee)).willReturn(employee);
				        employee.setEmail("ram@gmail.com");
				        employee.setFirstName("Ram");
				        // when -  action or the behavior that we are going test
				        Employee updatedEmployee = employeeService.updateEmployee(employee, 1L);

				        // then - verify the output
				        assertThat(updatedEmployee.getEmail()).isEqualTo("ram@gmail.com");
				        assertThat(updatedEmployee.getFirstName()).isEqualTo("Ram");
				    }
				 
				 @DisplayName("delete Employee")
				 @Test
				 public void givenEmployeeId_whenDeleteEmployee_thenNothing(){
				        // given - precondition or setup
				        long employeeId = 2L;
				    //    given(employeeRepository.findById(employee.getId())) .willReturn(Optional.of(employee));  
				        willDoNothing().given(employeeRepository).deleteById(employeeId);

				        // when -  action or the behavior that we are going test
				        employeeService.deleteEmployee(employeeId);

				        // then - verify the output
				        verify(employeeRepository, times(1)).deleteById(employeeId);
				    }

				

}
