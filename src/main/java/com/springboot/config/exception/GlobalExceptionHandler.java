package com.springboot.config.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice                   //this annotation will handle the exceptions globally
public class GlobalExceptionHandler {
	
	//handle specific exceptions
	@ExceptionHandler(ResourceNotFoundException.class)   // used to handle the specific exceptions and sending custom exception to the client
	public ResponseEntity<?> handleResouceNotfoundException(ResourceNotFoundException exception, WebRequest request){
		System.out.println("-----ResourceNotFoundException-----");
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
		
		return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
	}
	//handle global exceptions(to handle exception apart from the above exception)

	
	@ExceptionHandler(Exception.class)   // used to handle the specific exceptions and sending custom exception to the client
	public ResponseEntity<?> handleGlobalException(Exception exception, WebRequest request){
		System.out.println("-----GlobalException-----");
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
		
		return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	//handle specific exceptions
		@ExceptionHandler(APIException.class)   // used to handle the specific exceptions and sending custom exception to the client
		public ResponseEntity<?> handleAPIException(APIException exception, WebRequest request){
			System.out.println("-----APIException-----");
			ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
			return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
		}
}
