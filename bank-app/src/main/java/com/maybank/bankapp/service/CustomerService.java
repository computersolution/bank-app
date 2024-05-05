package com.maybank.bankapp.service;

import com.maybank.bankapp.entity.Customer;
import com.maybank.bankapp.exception.ResourceNotFoundException;

public interface CustomerService {

	Customer createCustomer(String customer);

	Customer getCustomerById(Long id) throws ResourceNotFoundException;

}
