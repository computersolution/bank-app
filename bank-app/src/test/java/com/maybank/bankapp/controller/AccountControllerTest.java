package com.maybank.bankapp.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.maybank.bankapp.entity.Account;
import com.maybank.bankapp.service.AccountService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc
public class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountService accountService;

	@Test
	public void testCreateAccount() throws Exception {
		Account createdAccount = new Account();
		createdAccount.setId(1L);
		when(accountService.createAccount(anyString(), anyLong())).thenReturn(createdAccount);
		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/createAccount").param("type", "Savings").param("customerId", "123")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
	}

	@Test
	public void testGetAccount() throws Exception {
		Account account = new Account();
		account.setId(1L);
		when(accountService.getAccountById(1L)).thenReturn(account);
		mockMvc.perform(MockMvcRequestBuilders.get("/accounts/1").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
	}

	@Test
	public void testDeposit() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/1/deposit").param("amount", "100.0")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testWithdraw() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/1/withdraw").param("amount", "50.0")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testCloseAccount() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/1/close").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
	}
}
