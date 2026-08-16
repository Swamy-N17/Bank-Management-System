package com.jsp.BankManagementSystem.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;

import com.jsp.BankManagementSystem.Dto.ResponseStructure;
import com.jsp.BankManagementSystem.Entity.Bank;
import com.jsp.BankManagementSystem.Exception.AddressNotFoundException;
import com.jsp.BankManagementSystem.Exception.ContactVerificationException;
import com.jsp.BankManagementSystem.Exception.IdNotFoundException;
import com.jsp.BankManagementSystem.Exception.NoRecordFoundException;
import com.jsp.BankManagementSystem.Exception.PincodeVerificationException;
import com.jsp.BankManagementSystem.Repository.AddressRepository;
import com.jsp.BankManagementSystem.Repository.BankRepository;

@Service
public class BankService {

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private AddressRepository addressRepository;
	
	


	public ResponseStructure<Bank> saveBank(Bank bank) {
		ResponseStructure<Bank> res = new ResponseStructure<>();

		// bank must have address verification
		if (bank.getAddress() == null)
			throw new AddressNotFoundException("Bank must have an address");

		// IFSC Validation
		if (bankRepository.existsByIfsc(bank.getIfsc()) == true)
			throw new DataIntegrityViolationException("IFSC code already exist");

		// contact size validation
		if (String.valueOf(bank.getContact()).length() != 10)
			throw new ContactVerificationException("Contact Number Must Contain 10 Digits");

		// Contact Validation
		if (bankRepository.existsByContact(bank.getContact()) == true)
			throw new DataIntegrityViolationException("Contact already exist");

		// Pincode validation
		if (addressRepository.existsByPinCode(bank.getAddress().getPinCode()) == true)
			throw new DataIntegrityViolationException("Pincode already exist");

		// Pincode size validation
		if (bank.getAddress().getPinCode().length() != 6)
			throw new PincodeVerificationException("Pincode Must Contain 6 Digits");

		res.setData(bankRepository.save(bank));
		res.setMessage("Bank Records Saved Sucessfully");
		res.setStatusCode(HttpStatus.CREATED.value());

		return res;
	}

	public ResponseStructure<List<Bank>> fetchAllBank() {
		ResponseStructure<List<Bank>> res = new ResponseStructure<List<Bank>>();
		res.setData(bankRepository.findAll());
		res.setMessage("All Banks Records Fetched Sucessfully");
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}

	public ResponseStructure<Bank> fetchByBankId(Integer id) {
		ResponseStructure<Bank> res = new ResponseStructure<Bank>();
		Optional<Bank> opt = bankRepository.findById(id);
		if (opt.isPresent()) {
			res.setData(opt.get());
			res.setMessage("Bank Record Having ID: " + id + " Fetched Sucessfully");
			res.setStatusCode(HttpStatus.OK.value());
			return res;
		}
		throw new IdNotFoundException("No Record Found Having Id: " + id + " in Database");
	}


	public ResponseStructure<String> deleteBankById(Integer id) {
		ResponseStructure<String> res = new ResponseStructure<String>();
		Optional<Bank> opt = bankRepository.findById(id);
		if (opt.isPresent()) {
			
			//Check Bank has Active Accounts or not
			if(opt.get().getAccount().isEmpty()) {
				bankRepository.deleteById(id);
				res.setData("Bank Record Having ID: " + id + " Deleted Sucessfully");
				res.setMessage("Sucesss");
				res.setStatusCode(HttpStatus.OK.value());
				return res;
			}
			else {
				throw new DataIntegrityViolationException("Bank cannot be deleted if it has accounts");
			}
		}
		throw new IdNotFoundException("No Record Found Having Id: " + id + " in Database");
	}

	public ResponseStructure<Bank> getByIfsc(String ifsc) {
		ResponseStructure<Bank> res = new ResponseStructure<>();
		Bank bank = bankRepository.findByIfsc(ifsc);
		if (bank == null)
			throw new NoRecordFoundException("Bank Record Having IFSC: " + ifsc + " Not Found");

		else {
			res.setMessage("Bank Details Fetched Having IFSC: " + ifsc);
			res.setData(bank);
			res.setStatusCode(HttpStatus.OK.value());
			return res;
		}
	}

	public ResponseStructure<Bank> getByAddress(Integer id) {

		ResponseStructure<Bank> res = new ResponseStructure<Bank>();
		Bank bank = bankRepository.findByAddressId(id);
		if (bank == null)
			throw new NoRecordFoundException("Bank Record Having Address Id: " + id + " Not Found");
		else {
			res.setMessage("Bank Details Fetched Having Pincode: " + id);
			res.setData(bank);
			res.setStatusCode(HttpStatus.OK.value());
			return res;

		}

	}

	public ResponseStructure<List<Bank>> getBankByCity(String city){
		ResponseStructure<List<Bank>> res = new ResponseStructure<>();
		List<Bank> bank = bankRepository.getByCity(city);
		if (bank.isEmpty())
			throw new NoRecordFoundException("Bank Doesn't Exist in City: "+city);
		else {
			res.setMessage("Bank Details Fetched Sucessfull Exists in City: "+city);
			res.setData(bank);
			res.setStatusCode(HttpStatus.OK.value());
			return res;

		}
	}
	public ResponseStructure<Bank> getBankBycontact(Long contact) {

		ResponseStructure<Bank> res = new ResponseStructure<Bank>();
		Bank bank = bankRepository.findBankByContact(contact);
		if (bank == null)
			throw new NoRecordFoundException("Bank with contact " + contact + " not found");
		else {
			res.setMessage("Bank Details Fetched Having Contact: " + contact);
			res.setData(bank);
			res.setStatusCode(HttpStatus.OK.value());
			return res;

		}

	}
	
	public ResponseStructure<Page<Bank>> getByPaginationAndSort(int pageNumber,int pageSize,String fieldName){
		Page<Bank> page = bankRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(fieldName).ascending()));
		
		ResponseStructure<Page<Bank>> res = new ResponseStructure<>();
		if(page.isEmpty())
			throw new NoRecordFoundException("No Bank Records Found In DB");
		else {
			res.setMessage("Records of PageNumber: "+pageNumber+" ,PageSize: "+pageSize+" and"+" Sorted By Field: "+fieldName);
		    res.setStatusCode(HttpStatus.OK.value());
		    res.setData(page);
		    
		    return res;
		}
	}
}
