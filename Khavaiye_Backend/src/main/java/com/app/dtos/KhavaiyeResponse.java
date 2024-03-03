package com.app.dtos;

import org.springframework.http.ResponseEntity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class KhavaiyeResponse {
	public static enum Status {
		SUCCESS, FAIL, ERROR
	}
	
	private Status status;
	private Object data;
	private String message;
	
	// success ctor
	public KhavaiyeResponse(Status status, Object data) {
		this.status = status;
		this.data = data;
	}
	
	// error ctor
	public KhavaiyeResponse(Status status, String message) {
		this.status = status;
		this.message = message;
	}
	
	// full ctor
	public KhavaiyeResponse(Status status, Object data, String message) {
		this.status = status;
		this.data = data;
		this.message = message;
	}

	// getters and setters
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static ResponseEntity<KhavaiyeResponse> success() {
		return ResponseEntity.ok(new KhavaiyeResponse(Status.SUCCESS, null));
	}
	
	public static ResponseEntity<KhavaiyeResponse> success(Object data) {
		return ResponseEntity.ok(new KhavaiyeResponse(Status.SUCCESS, data));
	}
	
	public static ResponseEntity<KhavaiyeResponse> success(String message, Object data) {
		return ResponseEntity.ok(new KhavaiyeResponse(Status.SUCCESS, data, message));
	}
	
	public static ResponseEntity<KhavaiyeResponse> fail(Object data) {
		return ResponseEntity.ok(new KhavaiyeResponse(Status.FAIL, data));
	}

	public static ResponseEntity<KhavaiyeResponse> fail(String message, Object data) {
		return ResponseEntity.ok(new KhavaiyeResponse(Status.FAIL, data, message));
	}
	
	public static ResponseEntity<KhavaiyeResponse> error(String message) {
		return ResponseEntity.ok(new KhavaiyeResponse(Status.ERROR, message));
	}

	public static ResponseEntity<KhavaiyeResponse> error(String message, Object data) {
		return ResponseEntity.ok(new KhavaiyeResponse(Status.ERROR, data, message));
	}
	
}
