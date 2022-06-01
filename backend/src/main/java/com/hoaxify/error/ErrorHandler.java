package com.hoaxify.error;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;


@RestController
public class ErrorHandler implements ErrorController {
	
	@Autowired
	private DefaultErrorAttributes defaultErrorAttributes;
	
	
	public String getErrorPath() {
		return "/error";
	}
	
	
	@RequestMapping("/error")
	 ResponseEntity<ApiError> handleError(WebRequest webRequest,ErrorAttributeOptions options) {
		Map<String, Object> attributes = this.defaultErrorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults().including(ErrorAttributeOptions.Include.MESSAGE,ErrorAttributeOptions.Include.BINDING_ERRORS));    
	    String message =(String)attributes.get("message");
	    String path = (String)attributes.get("path");
	    int status = (Integer)attributes.get("status");
	    ApiError apiError = new ApiError(status,message,path);
	    if(attributes.containsKey("errors")) {
	    	@SuppressWarnings("unchecked")
			List<FieldError> fieldError = (List<FieldError>)attributes.get("errors");
	    	Map<String,String> map = new HashMap<String, String>();
	    	for(FieldError f:fieldError) {
	    		 map.put(f.getField(), f.getDefaultMessage());
	      }
	    	apiError.setValidationErrors(map);
	    }
		return ResponseEntity.status(status).body(apiError);
	}


}
