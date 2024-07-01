package com.reece.branchmanager.addressbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.reece.branchmanager.addressbook.model.BaseResponse;
import com.reece.branchmanager.addressbook.model.ResponseMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public BaseResponse<Object> handleResourceNotFoundException(ResourceNotFoundException ex,
			final HttpServletRequest request) {
		log.error("Service exception caught {}", ex.getMessage());
		return ResponseMapper.createResponse(request, null, HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(),
				ex.getMessage());

	}
		  
	@ExceptionHandler(Exception.class)
	public BaseResponse<Object> handleOtherExceptions(Exception ex, final HttpServletRequest request) {
		log.error("Service exception caught {}", ex.getMessage());
		return ResponseMapper.createResponse(request, null, HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				ex.getMessage());
	}

}
