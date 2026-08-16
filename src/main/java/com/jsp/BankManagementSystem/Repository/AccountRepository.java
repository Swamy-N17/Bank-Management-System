package com.jsp.BankManagementSystem.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.BankManagementSystem.Entity.Account;
import com.jsp.BankManagementSystem.Entity.AccountType;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	boolean existsByAccountNumber(Long accountNumber);
	
	Optional<Account> findByAccountNumber(Long accountNumber);
	
	List<Account> findByAccountType(AccountType accountType);
	
	List<Account> findByAccountBalanceGreaterThan(Double value);
}
