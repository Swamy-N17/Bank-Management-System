package com.jsp.BankManagementSystem.Service;

import java.util.Map;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jsp.BankManagementSystem.Dto.ResponseStructure;
import com.jsp.BankManagementSystem.Entity.Address;
import com.jsp.BankManagementSystem.Entity.Bank;
import com.jsp.BankManagementSystem.Exception.IdNotFoundException;
import com.jsp.BankManagementSystem.Exception.NoRecordFoundException;
import com.jsp.BankManagementSystem.Repository.AddressRepository;
import com.jsp.BankManagementSystem.Repository.BankRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private BankRepository bankrepository;

	public ResponseStructure<Address> getAddressById(Integer id) {

		ResponseStructure<Address> res = new ResponseStructure<Address>();
		Optional<Address> opt = addressRepository.findById(id);
		if (opt.isPresent()) {
			res.setData(opt.get());
			res.setMessage("Address Record Fetched Sucessfully Having Id: " + id);
			res.setStatusCode(HttpStatus.OK.value());
			return res;
		} else
			throw new IdNotFoundException("Address ID " + id + " not found");
	}

	public ResponseStructure<Address> updateAddress(Map<String, Object> data, Integer id) {

		Optional<Address> opt = addressRepository.findById(id);
		ResponseStructure<Address> res = new ResponseStructure<>();
		if (opt.isPresent()) {
			Address address = opt.get();
			for (Map.Entry<String, Object> entry : data.entrySet()) {

				String key = entry.getKey();
				Object value = entry.getValue();

				switch (key) {
				case "street":
					address.setStreet((String) value);
					break;

				case "city":
					address.setCity((String) value);
					break;
				case "state":
					address.setState((String) value);
					break;
				case "pinCode":
					address.setPinCode((String) value);
					break;
				}
			}
			res.setData(addressRepository.save(address));
			res.setMessage("Address Updated Sucessfully");
			res.setStatusCode(HttpStatus.OK.value());
			return res;
		}
		else
			throw new IdNotFoundException("Address ID " + id + " not found");
	}
	
	
	public ResponseStructure<Address> getAddressByBank(Integer id)
	{
		ResponseStructure<Address> res = new ResponseStructure<Address>();
		Optional<Bank> opt = bankrepository.findById(id);
		
		if(opt.isPresent())
		{
			res.setMessage("Address Record Fetched Sucessfully From Bank Id: "+id );
			res.setData(opt.get().getAddress());
			res.setStatusCode(HttpStatus.OK.value());
			return res;
		}
		else
			throw new IdNotFoundException("Bank With ID: "+id+" Not Found");
	}

	public ResponseStructure<List<Address>> getAddressByCityAndStreet(String city, String street) {
		
		ResponseStructure<List<Address>> res = new ResponseStructure<>();
		List<Address> address = addressRepository.findAddressByCityAndStreet(city,street);
		if(address.isEmpty()) {
			
			throw new NoRecordFoundException("Address Record Not Found");
		}
		else {
			res.setData(address);
			res.setMessage("Address Record Fetched Sucessfully");
			res.setStatusCode(HttpStatus.OK.value());
			return res;
		}
		
		
	}

}
