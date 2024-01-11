package com.springSecurity.userManagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ResourceNotFoundException extends RuntimeException {

	  private String message;
	  private String errorCode;

	  private String resource;

	  public ResourceNotFoundException(String resource, String errorCode,String message) {
	    super(String.format("%s not found with error code %s", resource, HttpStatus.INTERNAL_SERVER_ERROR.name()));
	    this.resource = resource;
	    this.errorCode = errorCode;
	    this.message=message;
	  }

	  public String getResource() {
	    return resource;
	  }

	  public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorCode() {
	    return errorCode;
	  }
	}