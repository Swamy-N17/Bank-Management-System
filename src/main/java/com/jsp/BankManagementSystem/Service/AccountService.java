package com.jsp.BankManagementSystem.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jsp.BankManagementSystem.Dto.ResponseStructure;
import com.jsp.BankManagementSystem.Entity.Account;
import com.jsp.BankManagementSystem.Entity.AccountType;
import com.jsp.BankManagementSystem.Entity.Bank;
import com.jsp.BankManagementSystem.Exception.IdNotFoundException;
import com.jsp.BankManagementSystem.Exception.MinimumBalanceException;
import com.jsp.BankManagementSystem.Exception.NoRecordFoundException;
import com.jsp.BankManagementSystem.Repository.AccountRepository;
import com.jsp.BankManagementSystem.Repository.BankRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private BankRepository bankReposiory;

	public ResponseStructure<Account> saveAccount(Account account) {
		ResponseStructure<Account> res = new ResponseStructure<Account>();
		Optional<Bank> opt = bankReposiory.findById(account.getBank().getId());
		if (opt.isPresent()) {

			// Account Number Validation
			if (accountRepository.existsByAccountNumber(account.getAccountNumber()) == true)
				throw new DataIntegrityViolationException("Account Number Already Exists");

			// Minimum Balance for Savings and Current Account
			minimuBalanceVerifier(account);
			if (account.getAccountType() != AccountType.SAVINGS && account.getAccountType() != AccountType.CURRENT
					&& account.getAccountType() != AccountType.SALARY) {

				throw new DataIntegrityViolationException("Account type must be SAVINGS, CURRENT, or SALARY");
			}
			res.setData(accountRepository.save(account));
			res.setMessage("Account Saved Sucessfully");
			res.setStatusCode(HttpStatus.CREATED.value());
			return res;
		} else
			throw new IdNotFoundException("Bank Doesn't Found with ID: " + account.getBank().getId());
	}

	public ResponseStructure<List<Account>> fetchAllAccount() {

		ResponseStructure<List<Account>> res = new ResponseStructure<List<Account>>();
		List<Account> account = accountRepository.findAll();
		if (account.isEmpty())
			throw new NoRecordFoundException("Accounts Not Found");
		else {
			res.setData(account);
			res.setMessage("ALl Accounts Fetched Sucessfully");
			res.setStatusCode(HttpStatus.OK.value());
			return res;
		}
	}

	public ResponseStructure<Account> fetchAccountByID(Integer id) {

		ResponseStructure<Account> res = new ResponseStructure<>();
		Optional<Account> opt = accountRepository.findById(id);
		if (opt.isPresent()) {
			res.setData(opt.get());
			res.setMessage(" Account Fetched Sucessfully With ID: " + id);
			res.setStatusCode(HttpStatus.OK.value());
			return res;
		}

		else
			throw new IdNotFoundException("Accounts Not Found With ID: " + id);

	}

	public ResponseStructure<String> deleteBankById(Integer id) {
		ResponseStructure<String> res = new ResponseStructure<String>();
		Optional<Account> opt = accountRepository.findById(id);
		if (opt.isPresent()) {

			accountRepository.deleteById(id);
			res.setData("Account With ID: " + id + " Deleted Sucessfully");
			res.setMessage("Success");
			res.setStatusCode(HttpStatus.OK.value());
			return res;
		}

		else
			throw new IdNotFoundException("Accounts Not Found With ID: " + id);
	}

	public ResponseStructure<String> depositAmount(Double amount, Long accountNumber) {

		ResponseStructure<String> res = new ResponseStructure<String>();
		Optional<Account> opt = accountRepository.findByAccountNumber(accountNumber);

		if (opt.isPresent()) {
			{
				if (amount > 0)
					opt.get().setAccountBalance(opt.get().getAccountBalance() + amount);

				else
					throw new DataIntegrityViolationException("Amount must be greater than zero");
			}
			accountRepository.save(opt.get());
			res.setData("Amount Deposited Sucessfully, Available Balance: " + opt.get().getAccountBalance());
			res.setMessage("Success");
			res.setStatusCode(HttpStatus.OK.value());
			return res;
		} else {
			throw new IdNotFoundException("Account Not Found");
		}

	}

	public ResponseStructure<String> withdrawAmount(Long accountNumber, Double amount) {

		ResponseStructure<String> res = new ResponseStructure<String>();
		Optional<Account> opt = accountRepository.findByAccountNumber(accountNumber);
		if (opt.isPresent()) {

			// amount check positive or negative
			if (amount <= 0)
				throw new DataIntegrityViolationException("Amount must be greater than zero");
			else {
				// Sufficient balance check
				if (amount > opt.get().getAccountBalance())
					throw new DataIntegrityViolationException("Insufficent Money in Account");

				opt.get().setAccountBalance(opt.get().getAccountBalance() - amount);
				// Minimum Balance Verification
				minimuBalanceVerifier(opt.get());
			}
			accountRepository.save(opt.get());
			res.setData("Amount Withdrawn Successfully, Available Balance: " + opt.get().getAccountBalance());
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Success");
			return res;
		} else
			throw new IdNotFoundException("Account Not Found");
	}

	public ResponseStructure<String> TransferAmount(Long senderAccountNumber, Long receiverAccountNumber,
			Map<String, Double> data) {

		ResponseStructure<String> res = new ResponseStructure<String>();
		Optional<Account> optSender = accountRepository.findByAccountNumber(senderAccountNumber);
		Optional<Account> optReciver = accountRepository.findByAccountNumber(receiverAccountNumber);

		if (optSender.isPresent() && optReciver.isPresent()) {

			// sender != receiver check
			if (optSender.get().getAccountNumber().equals(optReciver.get().getAccountNumber()))
				throw new DataIntegrityViolationException("Sender and Reciver Must Not Be Same");

			Double amount = data.get("amount");

			// amount check positive or negative
			if (amount <= 0)
				throw new DataIntegrityViolationException("Amount must be greater than zero");
			else {

				// Sufficient balance check
				if (amount > optSender.get().getAccountBalance())
					throw new DataIntegrityViolationException(
							"Insufficent Money in Account With Number: " + optSender.get().getAccountNumber());

				optSender.get().setAccountBalance(optSender.get().getAccountBalance() - amount);

				// Minimum balance maintained check
				minimuBalanceVerifier(optSender.get());

				// Add to Receiver
				optReciver.get().setAccountBalance(optReciver.get().getAccountBalance() + amount);

				accountRepository.save(optSender.get());
				accountRepository.save(optReciver.get());

				res.setData("Amount Transfered Sucessfully");
				res.setMessage("Success");
				res.setStatusCode(HttpStatus.OK.value());
				return res;

			}
		} else
			throw new IdNotFoundException("Account Not Found");

	}

	public ResponseStructure<List<Account>> fetchAccountBankByID(Integer id) {

		ResponseStructure<List<Account>> res = new ResponseStructure<>();
		Optional<Bank> accounts = bankReposiory.findById(id);
		if (accounts.isEmpty())
			throw new IdNotFoundException("Bank Not Found With ID: " + id);

		else {
			res.setData(accounts.get().getAccount());
			res.setMessage(" Accounts Fetched Sucessfully");
			res.setStatusCode(HttpStatus.OK.value());
			return res;
		}

	}

	public ResponseStructure<List<Account>> findAccountByType(AccountType accountType) {
		ResponseStructure<List<Account>> res = new ResponseStructure<>();

		List<Account> accounts = accountRepository.findByAccountType(accountType);

		if (!accounts.isEmpty()) {
			res.setData(accounts);
			res.setMessage("Accounts Fetched Successfully for Type: " + accountType);
			res.setStatusCode(HttpStatus.OK.value());
			return res;
		} else {
			throw new NoRecordFoundException("No Accounts Available for Type: " + accountType);
		}
	}

	public ResponseStructure<List<Account>> getAccountBalanceGreaterThan(Double value) {

		ResponseStructure<List<Account>> res = new ResponseStructure<>();

		List<Account> accounts = accountRepository.findByAccountBalanceGreaterThan(value);

		if (!accounts.isEmpty()) {

			res.setData(accounts);
			res.setMessage("Accounts With Balance Greater Than " + value + " Fetched Successfully");
			res.setStatusCode(HttpStatus.OK.value());

			return res;
		}

		throw new NoRecordFoundException("No Accounts Found With Balance Greater Than " + value);
	}

	public ResponseStructure<Account> getAccountByAccountNumber(Long accountNumber) {

		ResponseStructure<Account> res = new ResponseStructure<>();

		Optional<Account> opt = accountRepository.findByAccountNumber(accountNumber);

		if (opt.isPresent()) {
			res.setData(opt.get());
			res.setMessage("Account Fetched Successfully");
			res.setStatusCode(HttpStatus.OK.value());
			return res;
		}

		throw new IdNotFoundException("Account Number " + accountNumber + " not found");
	}

	public ResponseStructure<List<Account>> getAccountBySorting(String fieldname) {
		ResponseStructure<List<Account>> res = new ResponseStructure<>();
		List<Account> account = accountRepository.findAll(Sort.by(fieldname).ascending());
		if (account.isEmpty())
			throw new NoRecordFoundException("Np Record Found");
		else {
			res.setData(account);
			res.setMessage("Accounts Sorted Successfully");
			res.setStatusCode(HttpStatus.OK.value());
			return res;
		}
	}

	// ------------------------------------------------------------------------------
	// helper method
	public void minimuBalanceVerifier(Account account) {
		if (account.getAccountType() == AccountType.SAVINGS && account.getAccountBalance() < 1000) {
			throw new MinimumBalanceException("Savings account requires minimum balance of ₹1000");
		}

		if (account.getAccountType() == AccountType.CURRENT && account.getAccountBalance() < 5000) {
			throw new MinimumBalanceException("Current account requires minimum balance of ₹5000");
		}
	}

}
