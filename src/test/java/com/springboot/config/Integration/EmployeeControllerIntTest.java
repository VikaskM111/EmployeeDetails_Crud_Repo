package com.springboot.config.Integration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.config.exception.GlobalExceptionHandler;
import com.springboot.config.exception.ResourceNotFoundException;
import com.springboot.config.model.Employee;
import com.springboot.config.repository.EmployeeRepository;
import com.springboot.config.service.EmployeeService;
import com.springboot.config.service.impl.EmployeeServiceImpl;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // this will start the integreted server on random port
@AutoConfigureMockMvc // we need to configure MockMvc class to call the REST APIs


public class EmployeeControllerIntTest {
	
	@Autowired
	private MockMvc mockMvc; // we are going to use MockMvc class to perform different Http class
	
	@Autowired
	private EmployeeRepository employeeRepository; // we will use deleteall() method from employee repository to delete the records from database to keep clean setup

	@Autowired
	private ObjectMapper objectMapper; //for serialization and deserialization
	
	@BeforeEach             //this method will be executed before each and every Junit test case
	void setUp() {
		employeeRepository.deleteAll();
	}
	
	
	// JUnit test to save employee REST API

	@Test
	public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee()throws Exception {
		
		Employee employee = Employee.builder()
				                    .firstName("")
				                    .lastName("kumar")
				                    .email("vikas@gmail.com")
				                    .build();
		
		
		
		ResultActions response = mockMvc.perform(post("/api/employees")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(employee)));
		
		response.andDo(print()).andExpect(status().isCreated())
		.andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
		.andExpect(jsonPath("$.lastName", is(employee.getLastName())))
		.andExpect(jsonPath("$.email", is(employee.getEmail())));
		
		EmployeeServiceImpl service  =  new EmployeeServiceImpl(employeeRepository);
		
//		GlobalExceptionHandler ex1 = assertThrows(GlobalExceptionHandler.class, 
//                () -> service.saveEmployee(employee));
		
		ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, 
                 () -> service.saveEmployee(employee));
	}
	
	
	// JUnit test for Get All employees REST API
//		@Test
		public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception{
			//in this senario we will save couple of records in database and then fetch the details
			List<Employee> listOfEmployee  = new ArrayList<>();
			listOfEmployee.add(Employee.builder().firstName("Ramesh").lastName("User").email("ramesh@gmail.com").build());
			listOfEmployee.add(Employee.builder().firstName("Toni").lastName("kroos").email("toni@gmail.com").build());
			employeeRepository.saveAll(listOfEmployee);// saves the data
	     // when -  action or the behaviour that we are going test
	        ResultActions response = mockMvc.perform(get("/api/employees"));
	        
	     // then - verify the output
	        response.andExpect(status().isOk())
	                .andDo(print())
	                .andExpect(jsonPath("$.size()", is(listOfEmployee.size())));

		}
		
		
		// positive scenario - valid employee id
	    // JUnit test for GET employee by id REST API
		
//		@Test
		public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception{
			// given - precondition or setup
			//first save the below employee details
	        Employee employee = Employee.builder()
	        		                    .firstName("vikas")
	        		                    .lastName("kumar")
	        		                    .email("vikas@gmail.com").build();
	        
	        employeeRepository.save(employee);
	        
	        // when -  action or the behaviour that we are going test
	        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));
	        
	        // then - verify the output
	        response.andExpect(status().isOk())
	                .andDo(print())
	                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
	                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
	                .andExpect(jsonPath("$.email", is(employee.getEmail())));		
		}
		
		 // negative scenario - valid employee id
	    // JUnit test for GET employee by id REST API
//		   @Test
		    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception{
		        // given - precondition or setup
		        long employeeId = 5L;
		        Employee employee = Employee.builder()
		                .firstName("toni")
		                .lastName("kroos")
		                .email("kroos@gmail.com")
		                .build();
		        
		        employeeRepository.save(employee);

		        // when -  action or the behaviour that we are going test
		        ResultActions response = mockMvc.perform(get("/api/employees/5", employeeId));

		        // then - verify the output
		        response.andExpect(status().isNotFound())
		                .andDo(print());

		    }
		   
		   
	//	   @Test
		    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception{
		        // given - precondition or setup
		        Employee savedEmployee = Employee.builder()
		        		
		                .firstName("toni")
		                .lastName("kroos")
		                .email("kroos@gmail.com")
		                .build();
		        
		        employeeRepository.save(savedEmployee);

		        //prepare the updated employee object
		        Employee updatedEmployee = Employee.builder()
		                .firstName("Ram")
		                .lastName("Jadhav")
		                .email("ram@gmail.com")
		                .build();
		       

		        // when -  action or the behaviour that we are going test
		        ResultActions response = mockMvc.perform(put("/api/employees/{id}", savedEmployee.getId())
		                                    .contentType(MediaType.APPLICATION_JSON)
		                                    .content(objectMapper.writeValueAsString(updatedEmployee)));


		        // then - verify the output
		        response.andExpect(status().isOk())
		                .andDo(print())
		                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
		                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
		                .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));
		    }
		   
		   // JUnit test for update employee REST API - negative scenario
		//    @Test
		    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception{
		        // given - precondition or setup
		        long employeeId = 1L;
		        Employee savedEmployee = Employee.builder()
		                .firstName("Ramesh")
		                .lastName("Fadatare")
		                .email("ramesh@gmail.com")
		                .build();
		        
		        employeeRepository.save(savedEmployee);

		        Employee updatedEmployee = Employee.builder()
		                .firstName("Ram")
		                .lastName("Jadhav")
		                .email("ram@gmail.com")
		                .build();
		       

		        // when -  action or the behaviour that we are going test
		        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
		                .contentType(MediaType.APPLICATION_JSON)
		                .content(objectMapper.writeValueAsString(updatedEmployee)));

		        // then - verify the output
		        response.andExpect(status().isNotFound())
		                .andDo(print());
		    }
		    
		 // JUnit test for delete employee REST API
	//	    @Test
		    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception{
		        // given - precondition or setup
		        
		        Employee savedEmployee = Employee.builder()
		                .firstName("ram")
		                .lastName("user_1")
		                .email("ram@gmail.com")
		                .build();
		        
		        employeeRepository.save(savedEmployee);

		        // when -  action or the behaviour that we are going test
		        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", savedEmployee.getId()));

		        // then - verify the output
		        response.andExpect(status().isOk())
		                .andDo(print());
		    }

		   
}
