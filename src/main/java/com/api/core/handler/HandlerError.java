package com.api.core.handler;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.api.core.exception.NotFoundException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice(annotations = RestController.class)
public class HandlerError {

	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(ConstraintViolationException.class)
	public Map<String, String> handleContrationViolationException(ConstraintViolationException ex) {
		return Map.of("message", ex.getMessage());
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public Map<String, String> handleErroNotFoundException(NotFoundException ex) {
		return Map.of("message", ex.getMessage());
	}

}
