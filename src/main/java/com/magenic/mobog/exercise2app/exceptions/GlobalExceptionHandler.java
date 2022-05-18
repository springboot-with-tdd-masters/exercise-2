package com.magenic.mobog.exercise2app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	// TODO add a message class for a more descriptive error message
	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<String> handleBookNotFoundException(InvalidRequestException ex, WebRequest req){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("request format invalid");
	}
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<String> handleOtherException(Exception ex, WebRequest req){
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occured.");
//	}
}
