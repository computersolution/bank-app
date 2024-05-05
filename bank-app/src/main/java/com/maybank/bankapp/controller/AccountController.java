package com.maybank.bankapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maybank.bankapp.entity.Account;
import com.maybank.bankapp.entity.Customer;
import com.maybank.bankapp.exception.ErrorResponse;
import com.maybank.bankapp.exception.ResourceNotFoundException;
import com.maybank.bankapp.service.AccountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller class for handling account-related REST endpoints.
 */

@RestController
@RequestMapping("accounts")
@Api(tags = { "Account REST endpoints" })
public class AccountController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    
    /**
     * Endpoint to create an account for a customer.
     *
     * @param type        The type of the account.
     * @param customerId  The ID of the customer for whom the account is created.
     * @return            ResponseEntity with the created account and HTTP status code.
     */
    
    @PostMapping(path = "/createAccount")
    @ApiOperation(value = "Create an account", notes = "Create an account for a customer")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<Account> createAccount(@RequestParam String type, @RequestParam Long customerId) {
        LOGGER.info("Received request to create an account. Type: {}, CustomerId: {}", type, customerId);
        Account createdAccount = null;
        try {
            createdAccount = accountService.createAccount(type, customerId);
            LOGGER.info("Account created successfully with ID: {}", createdAccount.getId());
        } catch (ResourceNotFoundException e) {
            LOGGER.error("Error occurred while creating account: {}", e.getMessage());
        }
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    
    /**
     * Endpoint to retrieve account details by ID.
     *
     * @param id  The ID of the account to retrieve.
     * @return    ResponseEntity with the account details and HTTP status code.
     */
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Get account details", notes = "Get account details by account Id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Customer.class, responseContainer = "Object"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<?> getAccount(@PathVariable Long id) {
        LOGGER.info("Received request to get account details for ID: {}", id);
        Account account = null;
        try {
            account = accountService.getAccountById(id);
            LOGGER.info("Retrieved account details successfully for ID: {}", id);
        } catch (ResourceNotFoundException e) {
            LOGGER.error("Error occurred while getting account details for ID {}: {}", id, e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(404, "Account not found with id: " +id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok(account);
    }

    /**
     * Endpoint to deposit an amount into the specified account.
     *
     * @param id      The ID of the account to deposit the amount into.
     * @param amount  The amount to deposit.
     * @return        ResponseEntity with the updated account details and HTTP status code.
     */
    
    @PostMapping("/{id}/deposit")
    @ApiOperation(value = "Deposit an amount", notes = "Deposit an amount of a customer")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<?> deposit(@PathVariable Long id, @RequestParam double amount) {
        LOGGER.info("Received request to deposit {} amount for account ID: {}", amount, id);
        Account account = null;
        try {
        	account = accountService.deposit(id, amount);
            LOGGER.info("Amount {} deposited successfully for account ID: {}", amount, id);
        } catch (ResourceNotFoundException e) {
            LOGGER.error("Error occurred while depositing amount {} for account ID {}: {}", amount, id, e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(404, "Account not found with id: " +id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok(account);
    }

    
    /**
     * Endpoint to withdraw an amount from the specified account.
     *
     * @param id      The ID of the account to withdraw the amount from.
     * @param amount  The amount to withdraw.
     * @return        ResponseEntity with the updated account details and HTTP status code.
     */
    @PostMapping("/{id}/withdraw")
    @ApiOperation(value = "Withdraw an amount", notes = "Withdraw an amount of a customer")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<?> withdraw(@PathVariable Long id, @RequestParam double amount) {
        LOGGER.info("Received request to withdraw {} amount for account ID: {}", amount, id);
        Account account = null;
        try {
        	account = accountService.withdraw(id, amount);
            LOGGER.info("Amount {} withdrawn successfully for account ID: {}", amount, id);
        } catch (ResourceNotFoundException e) {
            LOGGER.error("Error occurred while withdrawing amount {} for account ID {}: {}", amount, id, e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(404, "Account not found with id: " +id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok(account);
    }

    
    /**
     * Endpoint to close an account with the specified ID.
     *
     * @param id  The ID of the account to be closed.
     * @return    ResponseEntity with the closed account details and HTTP status code.
     */
    
    @PostMapping("/{id}/close")
    @ApiOperation(value = "Close an account", notes = "Close an account of a user")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<?> closeAccount(@PathVariable Long id) {
        LOGGER.info("Received request to close account for ID:", id);
        Account account = null;
        try {
        	account = accountService.closeAccount(id);
            LOGGER.info("Account closed successfully for ID: {}", id);
        } catch (ResourceNotFoundException e) {
            LOGGER.error("Error occurred while closing account for ID {}: {}", id, e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(404, "Account not found with id: " +id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok(account);
    }
}

