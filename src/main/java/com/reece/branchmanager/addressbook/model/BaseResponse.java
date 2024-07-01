package com.reece.branchmanager.addressbook.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
	
	private int statusCode;
	private Date timestamp;
	private String message;
	private String description;
	private T data;
	private String method;
	private String path;

}
