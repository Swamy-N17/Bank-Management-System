package com.jsp.BankManagementSystem.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.BankManagementSystem.Dto.ResponseStructure;
import com.jsp.BankManagementSystem.Entity.Account;
import com.jsp.BankManagementSystem.Entity.AccountType;
import com.jsp.BankManagementSystem.Service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping
	public ResponseEntity<ResponseStructure<Account>> saveAccount(@RequestBody Account account) {

		return new ResponseEntity<>(accountService.saveAccount(account), HttpStatus.CREATED);
	}

	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<Account>>> fetchAllAccount() {

		return new ResponseEntity<>(accountService.fetchAllAccount(), HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<ResponseStructure<Account>> findAccountById(@PathVariable Integer id) {

		return new ResponseEntity<>(accountService.fetchAccountByID(id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteAccountById(@PathVariable Integer id) {

		return new ResponseEntity<>(accountService.deleteBankById(id), HttpStatus.CREATED);
	}

	@PatchMapping("deposit/{amount}/{accountNumber}")
	public ResponseEntity<ResponseStructure<String>> depositAmountToAccount(@PathVariable Double amount,
			@PathVariable Long accountNumber) {

		return new ResponseEntity<>(accountService.depositAmount(amount, accountNumber), HttpStatus.OK);
	}

	@PatchMapping("withdraw/{amount}/{accountNumber}")
	public ResponseEntity<ResponseStructure<String>> withDrawAmountFromAccount(@PathVariable Double amount,
			@PathVariable Long accountNumber) {

		return new ResponseEntity<>(accountService.withdrawAmount(accountNumber, amount), HttpStatus.OK);
	}

	@PatchMapping("transfer/{senderAccountNumber}/{receiverAccountNumber}")
	public ResponseEntity<ResponseStructure<String>> transferAmountFromAccount(@RequestBody Map<String, Double> data,
			@PathVariable Long senderAccountNumber, @PathVariable Long receiverAccountNumber) {

		return new ResponseEntity<>(accountService.TransferAmount(senderAccountNumber, receiverAccountNumber, data),
				HttpStatus.OK);
	}

	@GetMapping("/bank/{bankId}")
	public ResponseEntity<ResponseStructure<List<Account>>> findAccountByBankId(@PathVariable Integer bankId) {

		return new ResponseEntity<>(accountService.fetchAccountBankByID(bankId), HttpStatus.OK);
	}

	@GetMapping("/type/{accountType}")
	public ResponseEntity<ResponseStructure<List<Account>>> findAccountByType(@PathVariable AccountType accountType) {

		return new ResponseEntity<>(accountService.findAccountByType(accountType), HttpStatus.OK);
	}

	@GetMapping("/balance/greater-than/{value}")
	public ResponseEntity<ResponseStructure<List<Account>>> getAccountBalanceGreaterThan(@PathVariable Double value) {

		return new ResponseEntity<>(accountService.getAccountBalanceGreaterThan(value), HttpStatus.OK);
	}

	@GetMapping("/number/{accountNumber}")
	public ResponseEntity<ResponseStructure<Account>> getAccountByAccountNumber(@PathVariable Long accountNumber) {

		return new ResponseEntity<>(accountService.getAccountByAccountNumber(accountNumber), HttpStatus.OK);
	}
	
	@GetMapping("/sort/{fieldname}")
	public ResponseEntity<ResponseStructure<List<Account>>> getAccountBySorting(@PathVariable String fieldname) {

		return new ResponseEntity<>(accountService.getAccountBySorting(fieldname), HttpStatus.OK);
	}
	
	
}
