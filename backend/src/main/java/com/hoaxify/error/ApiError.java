package com.hoaxify.error;
import java.util.Date;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
	private int status;
	private String message;
	private long timestap = new Date().getTime();
	private String path;
	private Map<String,String> validationErrors;
	
	public ApiError(int status,String message, String path) {
		this.status=status;
		this.message=message;
		this.path=path;
	
	}

}

