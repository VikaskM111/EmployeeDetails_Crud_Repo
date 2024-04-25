package com.springboot.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.config.model.Employee;
import com.springboot.config.service.EmployeeService;





@WebMvcTest
public class EmployeeControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EmployeeService employeeService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee()throws Exception {
		
		Employee employee = Employee.builder().firstName("vikas").lastName("kumar")
				            .email("vikas@gmail.com").build();
		
		given(employeeService.saveEmployee(any(Employee.class)))
        .willAnswer((invocation)-> invocation.getArgument(0));
		
		ResultActions response = mockMvc.perform(post("/api/employees")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(employee)));
		
		response.andDo(print()).andExpect(status().isCreated())
		.andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
		.andExpect(jsonPath("$.lastName", is(employee.getLastName())))
		.andExpect(jsonPath("$.email", is(employee.getEmail())));
	}
	
	// JUnit test for Get All employees REST API
	@Test
	public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception{
		List<Employee> listOfEmployee  = new ArrayList<>();
		listOfEmployee.add(Employee.builder().firstName("Vikas").lastName("Kumar").email("vikash@gmail.com").build());
		//listOfEmployee.add(Employee.builder().firstName("Toni").lastName("kroos").email("toni@gmail.com").build());
        given(employeeService.getAllEmployees()).willReturn(listOfEmployee);	
        
     // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees"));
        
     // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(listOfEmployee.size())));

	}
	
	// positive scenario - valid employee id
    // JUnit test for GET employee by id REST API
	
	@Test
	public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception{
		// given - precondition or setup
        long employeeId = 2L;
        Employee employee = Employee.builder()
        		                    .firstName("vikas")
        		                    .lastName("kumar")
        		                    .email("vikas@gmail.com").build();
        
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));
        //given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));
        
        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));		
	}
	
	 // negative scenario - valid employee id
    // JUnit test for GET employee by id REST API
	   @Test
	    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception{
	        // given - precondition or setup
	        long employeeId = 5L;
	        Employee employee = Employee.builder()
	                .firstName("toni")
	                .lastName("kroos")
	                .email("kroos@gmail.com")
	                .build();
	        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

	        // when -  action or the behaviour that we are going test
	        ResultActions response = mockMvc.perform(get("/api/employees/5", employeeId));

	        // then - verify the output
	        response.andExpect(status().isNotFound())
	                .andDo(print());

	    }
    // JUnit test for update employee REST API - positive scenario
	   
	public void updateEmployee() {
		String employeeName = "Ram";
	//	Employee employee = new Employee(, employeeName, employeeName, employeeName);
	}   
	   
	   
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception{
        // given - precondition or setup
        long id = 1L;
        Employee savedEmployee = Employee.builder()
        		
                .firstName("toni")
                .lastName("kroos")
                .email("kroos@gmail.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Ram")
                .lastName("Jadhav")
                .email("ram@gmail.com")
                .build();
        given(employeeService.getEmployeeById(id)).willReturn(Optional.of(savedEmployee));
        given(employeeService.updateEmployee(any(Employee.class), id))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", id)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(updatedEmployee)));


        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));
    }
   
}
