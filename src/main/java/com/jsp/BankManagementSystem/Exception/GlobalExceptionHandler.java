package com.jsp.BankManagementSystem.Exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jsp.BankManagementSystem.Dto.ResponseStructure;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ResponseStructure<String>> duplicateException(DataIntegrityViolationException exception)
	{
		ResponseStructure<String> res = new ResponseStructure<String>();
		res.setMessage(exception.getMessage());
		res.setStatusCode(HttpStatus.CONFLICT.value());
		res.setData("Failure");
		
		return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(ContactVerificationException.class)
	public ResponseEntity<ResponseStructure<String>> contactVerification(ContactVerificationException exception)
	{
		ResponseStructure<String> res = new ResponseStructure<String>();
		res.setMessage(exception.getMessage());
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setData("Failure");
		return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(PincodeVerificationException.class)
	public ResponseEntity<ResponseStructure<String>> pinCodeVerification(PincodeVerificationException exception)
	{
		ResponseStructure<String> res = new ResponseStructure<String>();
		res.setMessage(exception.getMessage());
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setData("Failure");
		return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(AddressNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> addressVerification(AddressNotFoundException exception)
	{
		ResponseStructure<String> res = new ResponseStructure<String>();
		res.setMessage(exception.getMessage());
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setData("Failure");
		return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(NoRecordFoundException.class)
	public ResponseEntity<ResponseStructure<String>> noRecord(NoRecordFoundException exception)
	{
		ResponseStructure<String> res = new ResponseStructure<String>();
		res.setMessage(exception.getMessage());
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setData("Failure");
		return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleINFE(IdNotFoundException exception)
	{
		ResponseStructure<String> res = new ResponseStructure<String>();
		res.setMessage(exception.getMessage());
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setData("Failure");
		
		return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(MinimumBalanceException.class)
	public ResponseEntity<ResponseStructure<String>> minimumBalance(MinimumBalanceException exception)
	{
		ResponseStructure<String> res = new ResponseStructure<String>();
		res.setMessage(exception.getMessage());
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setData("Failure");
		
		return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.NOT_FOUND);
	}
}
