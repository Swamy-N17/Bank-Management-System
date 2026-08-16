package com.jsp.BankManagementSystem.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStructure<T>{

	private int statusCode;
	private String message;
	private  T data;
	public int getStatusCode() {
		return statusCode;
	}

}

