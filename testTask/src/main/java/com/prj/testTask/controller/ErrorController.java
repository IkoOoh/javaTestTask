package com.prj.testTask.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.prj.testTask.dto.ErrorResponse;

@ControllerAdvice
public class ErrorController {
	
	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<ErrorResponse> handleInvalidFormatException(InvalidFormatException ex) {
	    String fieldName = ex.getPath().stream()
	                          .map(reference -> reference.getFieldName())
	                          .findFirst()
	                          .orElse("unknown");

	    String fieldValue = ex.getValue() != null 
	                        ? ex.getValue().toString() 
	                        : "null";

	    String errorMessage = String.format("Invalid format for field '%s': value '%s'", fieldName, fieldValue);

	    ErrorResponse errorResponse = new ErrorResponse("InvalidFormatException", errorMessage);
	    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
	    Throwable cause = ex.getCause();
	    if (cause instanceof InvalidFormatException) {
	        return handleInvalidFormatException((InvalidFormatException) cause);
	    }
	    
	    ErrorResponse errorResponse = new ErrorResponse(
	        "HttpMessageNotReadableException", 
	        "Invalid JSON input or unreadable message."
	    );
	    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
    	System.out.println("Caught exception type: " + ex.getClass().getName());
        ErrorResponse errorResponse = new ErrorResponse("Exception", "An unexpected error occurred.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "NoResourceFoundException", 
                "Resource not found"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
