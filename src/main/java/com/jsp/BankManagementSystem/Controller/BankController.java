package com.jsp.BankManagementSystem.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.BankManagementSystem.Dto.ResponseStructure;
import com.jsp.BankManagementSystem.Entity.Bank;
import com.jsp.BankManagementSystem.Service.BankService;

@RestController
@RequestMapping("/bank")
public class BankController {

	@Autowired
	BankService bankService;

	@PostMapping
	public ResponseEntity<ResponseStructure<Bank>> saveBank(@RequestBody Bank bank) {
		return new ResponseEntity<>(bankService.saveBank(bank), HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<Bank>>> fetchAllBank() {
		return new ResponseEntity<>(bankService.fetchAllBank(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Bank>> fetchBankById(@PathVariable Integer id) {
		return new ResponseEntity<>(bankService.fetchByBankId(id), HttpStatus.OK);
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deledeleteBankById(@PathVariable Integer id) {
		return new ResponseEntity<>(bankService.deleteBankById(id), HttpStatus.OK);
	}

	@GetMapping("/ifsc/{ifsc}")
	public ResponseEntity<ResponseStructure<Bank>> getByIfsc(@PathVariable String ifsc) {
		return new ResponseEntity<>(bankService.getByIfsc(ifsc), HttpStatus.OK);
	}

	@GetMapping("/address/{id}")
	public ResponseEntity<ResponseStructure<Bank>> getByAddress(@PathVariable Integer id) {
		return new ResponseEntity<>(bankService.getByAddress(id), HttpStatus.OK);
	}
	
	@GetMapping("/city/{city}")
	public ResponseEntity<ResponseStructure<List<Bank>>> getBankByCity(@PathVariable String city)
	{
		return new ResponseEntity<>(bankService.getBankByCity(city),HttpStatus.OK);
	}
	
	@GetMapping("/contact/{contact}")
	public ResponseEntity<ResponseStructure<Bank>> getBankByContact(@PathVariable Long contact)
	{
		return new ResponseEntity<>(bankService.getBankBycontact(contact),HttpStatus.OK);
	}
	
	@GetMapping("/{pageNumber}/{pageSize}/{fieldName}")
	public ResponseEntity<ResponseStructure<Page<Bank>>> getByPaginationAndSort(@PathVariable int pageNumber, @PathVariable int pageSize, @PathVariable String fieldName)
	{
		return new ResponseEntity<>(bankService.getByPaginationAndSort(pageNumber, pageSize, fieldName),HttpStatus.OK);
	}
}
