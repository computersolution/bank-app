package com.maybank.bankapp.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.maybank.bankapp.entity.Customer;
import com.maybank.bankapp.service.CustomerService;

public class CustomerControllerTest {

	private MockMvc mockMvc;

	private CustomerService customerService;
	private CustomerController customerController;

	@BeforeEach
	void setUp() {
		customerService = mock(CustomerService.class);
		customerController = new CustomerController(customerService);
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}

	@Test
	public void testCreateCustomer() throws Exception {
		String requestBody = "TestUser1";

		Customer createdCustomer = new Customer();
		createdCustomer.setId(1L);
		createdCustomer.setName("TestUser1");

		when(customerService.createCustomer(anyString())).thenReturn(createdCustomer);

		mockMvc.perform(post("/customers/createCustomer").contentType(MediaType.APPLICATION_JSON).content(requestBody))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.name").value("TestUser1"));
	}

	@Test
	public void testGetCustomer() throws Exception {
		Customer customer = new Customer();
		customer.setId(1L);
		customer.setName("TestUser2");

		when(customerService.getCustomerById(1L)).thenReturn(customer);

		mockMvc.perform(get("/customers/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L)).andExpect(jsonPath("$.name").value("TestUser2"));
	}
}
