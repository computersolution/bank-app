package com.maybank.bankapp.service;

import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maybank.bankapp.entity.Account;
import com.maybank.bankapp.entity.Customer;
import com.maybank.bankapp.exception.ResourceNotFoundException;
import com.maybank.bankapp.repository.AccountRepository;
import com.maybank.bankapp.repository.CustomerRepository;

/**
 * Implementation of the {@link AccountService} interface providing functionality
 * related to account management.
 */

@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CustomerRepository customerRepository;

	/**
     * Creates a new account for a customer.
     *
     * @param type       The type of the account to create.
     * @param customerId The ID of the customer for whom the account is being created.
     * @return The newly created account.
     * @throws ResourceNotFoundException if the customer with the given ID is not found.
     */
	@Override
	public Account createAccount(String type, Long customerId) throws ResourceNotFoundException {
		LOGGER.info("Creating account of type {} for customer with ID: {}", type, customerId);
		Random random = new Random();
		long accountNumber = Math.abs(random.nextLong()) % 10000000000L;
		Account account = new Account();
		account.setId(accountNumber);
		account.setType(type);
		account.setStatus("Active");

		Optional<Customer> customerOptional = customerRepository.findById(customerId);
		if (customerOptional.isPresent()) {
			Customer customer = customerOptional.get();
			account.setCustomer(customer);
		} else {
			LOGGER.error("Customer with ID {} not found. Cannot create account.", customerId);
			throw new ResourceNotFoundException(
					"Cannot create account for customer id:" + customerId + " as customer id is not exist");
		}

		Account createdAccount = accountRepository.save(account);
		LOGGER.info("Account created successfully with ID: {}", createdAccount.getId());
		return createdAccount;
	}

	/**
     * Retrieves the account details for the given account ID.
     *
     * @param id The ID of the account to retrieve.
     * @return The account details.
     * @throws ResourceNotFoundException if the account with the given ID is not found.
     */
	@Override
	public Account getAccountById(Long id) throws ResourceNotFoundException {
		LOGGER.info("Retrieving account details for ID: {}", id);

		// Retrieve the account details from the repository
		Account account = accountRepository.findById(id).orElseThrow(() -> {
			LOGGER.error("Account not found with id: {}", id);
			return new ResourceNotFoundException("Account not found with id: " + id);
		});

		return account;
	}

	 /**
     * Deposits the specified amount into the account with the given ID.
     *
     * @param accountId The ID of the account to deposit into.
     * @param amount    The amount to deposit.
     * @return The updated account details after the deposit.
     * @throws ResourceNotFoundException if the account with the given ID is not found.
     */
	@Override
	public Account deposit(Long accountId, double amount) throws ResourceNotFoundException {
		LOGGER.info("Depositing {} amount into account with ID: {}", amount, accountId);
		Account account = getAccountById(accountId);
		account.setBalance(account.getBalance() + amount);
		Account depositAccount = accountRepository.save(account);
		LOGGER.info("Amount {} deposited successfully into account with ID: {}", amount, accountId);
		return depositAccount;
	}

	 /**
     * Withdraws the specified amount from the account with the given ID.
     *
     * @param accountId The ID of the account to withdraw from.
     * @param amount    The amount to withdraw.
     * @return The updated account details after the withdrawal.
     * @throws ResourceNotFoundException if the account with the given ID is not found or if there are insufficient funds.
     */
	@Override
	public Account withdraw(Long accountId, double amount) throws ResourceNotFoundException {
		LOGGER.info("Withdrawing {} amount from account with ID: {}", amount, accountId);
		Account account = getAccountById(accountId);
		if (account.getBalance() < amount) {
			LOGGER.error("Insufficient funds in account: {}", accountId);
			throw new ResourceNotFoundException("Insufficient funds in account: " + accountId);
		}
		account.setBalance(account.getBalance() - amount);
		Account withdrawAccount = accountRepository.save(account);
		LOGGER.info("Amount {} withdrawn successfully from account with ID: {}", amount, accountId);
		return withdrawAccount;
	}

	/**
     * Closes the account with the given ID.
     *
     * @param accountId The ID of the account to close.
     * @return The closed account details.
     * @throws ResourceNotFoundException if the account with the given ID is not found.
     */
	@Override
	public Account closeAccount(Long accountId) throws ResourceNotFoundException {
		LOGGER.info("Closing account with ID: {}", accountId);
		Account closedAccount = getAccountById(accountId);
		closedAccount.setStatus("Closed");
		accountRepository.save(closedAccount);
		LOGGER.info("Account with ID {} closed successfully", accountId);
		return closedAccount;
	}
}
