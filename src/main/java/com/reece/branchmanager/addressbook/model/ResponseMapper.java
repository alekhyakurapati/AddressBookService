package com.reece.branchmanager.addressbook.model;

import java.util.Date;

import org.springframework.http.HttpStatus;

import jakarta.servlet.http.HttpServletRequest;

public class ResponseMapper {
	
	public static  <T> BaseResponse<T> createResponse(final HttpServletRequest httpServletRequest, final T response, HttpStatus status, int code, String message){
		
		BaseResponse<T> baseResponse = new BaseResponse<T>();
		
		baseResponse.setData(response);
		baseResponse.setStatusCode(code);
		baseResponse.setDescription(status.getReasonPhrase());
		baseResponse.setMessage(message);
		baseResponse.setTimestamp(new Date());
		baseResponse.setMethod(httpServletRequest.getMethod());
		baseResponse.setPath(httpServletRequest.getContextPath() + httpServletRequest.getServletPath());
		
		return baseResponse;
		
	}

}
