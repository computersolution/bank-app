package com.maybank.bankapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maybank.bankapp.entity.Customer;
import com.maybank.bankapp.exception.ResourceNotFoundException;
import com.maybank.bankapp.repository.CustomerRepository;

/**
 * Implementation of the {@link CustomerService} interface providing functionality
 * related to customer management.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Creates a new customer with the given name.
     *
     * @param name The name of the customer to create.
     * @return The newly created customer.
     */
    @Override
    public Customer createCustomer(String name) {
        LOGGER.info("Creating customer with name: {}", name);
        Customer customer = new Customer();
        customer.setName(name);
        Customer createdCustomer = customerRepository.save(customer);
        LOGGER.info("Customer created successfully with ID: {}", createdCustomer.getId());
        return createdCustomer;
    }

    /**
     * Retrieves the customer details for the given customer ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return The customer details.
     * @throws ResourceNotFoundException if the customer with the given ID is not found.
     */
    @Override
    public Customer getCustomerById(Long id) throws ResourceNotFoundException {
        LOGGER.info("Retrieving customer details for ID: {}", id);
        return customerRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Customer not found with id: {}", id);
                    return new ResourceNotFoundException("Customer not found with id: " + id);
                });
    }
}
