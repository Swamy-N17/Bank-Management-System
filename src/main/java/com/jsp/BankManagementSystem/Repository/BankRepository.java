package com.jsp.BankManagementSystem.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jsp.BankManagementSystem.Entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Integer> {

	boolean existsByIfsc(String ifsc);
	
	boolean existsByContact(Long contact);
	
	Bank findByIfsc(String ifsc);
	
	Bank findByAddressId(Integer id);
	
	@Query("select b from Bank b where b.address.city=:city")
	List<Bank> getByCity(String city);
	
	Bank findBankByContact(Long contact);
}
