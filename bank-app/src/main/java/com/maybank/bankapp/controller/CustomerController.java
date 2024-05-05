package com.maybank.bankapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maybank.bankapp.entity.Customer;
import com.maybank.bankapp.exception.ErrorResponse;
import com.maybank.bankapp.exception.ResourceNotFoundException;
import com.maybank.bankapp.service.CustomerService;

import io.micrometer.core.instrument.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller class for handling HTTP requests related to customers.
 * Provides REST endpoints for creating and retrieving customer details.
 */
@RestController
@RequestMapping("customers")
@Api(tags = { "Customer REST endpoints" })
@CrossOrigin(origins="*",allowedHeaders = "*")
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    /**
     * Constructs a new CustomerController with the specified CustomerService.
     * @param customerService The CustomerService used to handle customer-related operations.
     */
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Endpoint to create a new customer with the provided name.
     *
     * @param customerName The name of the customer to be created.
     * @return ResponseEntity with the created customer details and HTTP status code.
     */
    
    @PostMapping(path = "/createCustomer")
    @ApiOperation(value = "Add a Customer", notes = "Create a customer and then create an account of a customer")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<?> createCustomer(@RequestBody String customerName) {
    	if(StringUtils.isEmpty(customerName.replaceAll("[^\\p{L}\\p{N}]", "")) || customerName.length() == 0){
    		 ErrorResponse errorResponse = new ErrorResponse(400, "Customer Name Should Not be empty");
	         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    	}
        LOGGER.info("Received request to create a customer with name: {}", customerName);
        Customer createdCustomer = customerService.createCustomer(customerName);
        LOGGER.info("Customer created successfully with ID: {}", createdCustomer.getId());
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    /**
     * Endpoint to retrieve customer details by customer ID.
     *
     * @param customerId The ID of the customer to retrieve details for.
     * @return ResponseEntity with the retrieved customer details and HTTP status code.
     */
    
    @GetMapping(path = "/{customerId}")
    @ApiOperation(value = "Get customer details", notes = "Get Customer details by customer Id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Customer.class, responseContainer = "Object"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    public ResponseEntity<?> getCustomer(@PathVariable Long customerId) {
        LOGGER.info("Received request to get customer details for ID: {}", customerId);
        Customer customer = null;
		try {
			customer = customerService.getCustomerById(customerId);
		} catch (ResourceNotFoundException e) {
			LOGGER.error("Error occurred while getting the customer for ID {}: {}", customerId, e.getMessage());
			 ErrorResponse errorResponse = new ErrorResponse(404, "Account not found with id: " +customerId);
	         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
        LOGGER.info("Retrieved customer details successfully for ID: {}", customerId);
        return ResponseEntity.ok(customer);
    }
}

