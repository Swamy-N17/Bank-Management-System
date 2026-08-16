package com.jsp.BankManagementSystem.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.BankManagementSystem.Dto.ResponseStructure;
import com.jsp.BankManagementSystem.Entity.Address;
import com.jsp.BankManagementSystem.Service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	private AddressService addressService;
	@GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Address>> getAddressById(@PathVariable Integer id){
    	
		return new ResponseEntity<>(addressService.getAddressById(id),HttpStatus.OK);
    }
	
	@PatchMapping("/{id}")
	public ResponseEntity<ResponseStructure<Address>> updateAddress(@RequestBody Map<String,Object> data,@PathVariable Integer id){
		return new ResponseEntity<>(addressService.updateAddress(data, id),HttpStatus.OK);
	}
	
	@GetMapping("/bank/{id}")
	  public ResponseEntity<ResponseStructure<Address>> getAddressByBank(@PathVariable Integer id){
    	
			return new ResponseEntity<>(addressService.getAddressByBank(id),HttpStatus.OK);
	    }
	
	@GetMapping("/{city}/{street}")
	  public ResponseEntity<ResponseStructure<List<Address>>> getAddressByCityAndStreet(@PathVariable String city,@PathVariable String street){
    	
			return new ResponseEntity<>(addressService.getAddressByCityAndStreet(city,street),HttpStatus.OK);
	    }
}
