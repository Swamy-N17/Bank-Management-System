package com.jsp.BankManagementSystem.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.BankManagementSystem.Entity.Address;


public interface AddressRepository extends JpaRepository<Address, Integer> {

	boolean  existsByPinCode(String pinCode);
	
	List<Address> findAddressByCityAndStreet(String city,String Street);
}
