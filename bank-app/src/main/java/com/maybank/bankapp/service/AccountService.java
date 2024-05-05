package com.maybank.bankapp.service;

import com.maybank.bankapp.entity.Account;
import com.maybank.bankapp.exception.ResourceNotFoundException;

public interface AccountService {

	Account createAccount(String type, Long customerId) throws ResourceNotFoundException;

	Account getAccountById(Long id) throws ResourceNotFoundException;

	Account deposit(Long accountId, double amount) throws ResourceNotFoundException;

	Account withdraw(Long accountId, double amount) throws ResourceNotFoundException;

	Account closeAccount(Long accountId) throws ResourceNotFoundException;

}
