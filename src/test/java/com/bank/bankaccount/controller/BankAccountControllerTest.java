package com.bank.bankaccount.controller;

import com.bank.bankaccount.dto.BankAccountDTO;
import com.bank.bankaccount.service.BankAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.bank.bankaccount.config.SecurityConfig;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
@Import(SecurityConfig.class)
@WithMockUser(roles = "ADMIN")
class BankAccountControllerTest {
    @Value("${app.security.admin-user}")
    private String adminUsername;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankAccountService bankAccountService;

    @Test
    void shouldCreateAccount() throws Exception {
        // Arrange
        BankAccountDTO response = new BankAccountDTO();
        response.setAccountHolder("Joshua");
        when(bankAccountService.createAccount(any())).thenReturn(response);
        // Act & Assert
        mockMvc.perform(post("/bank")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountHolder\":\"Joshua\", \"accountNumber\":\"12345\", \"balance\":100.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountHolder").value("Joshua"));
    }

    @Test
    void shouldFindAccountByNumber() throws Exception {
        // Arrange
        BankAccountDTO response = new BankAccountDTO();
        response.setAccountNumber("12345");

        when(bankAccountService.getBankAccountByAccountNumber("12345")).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/bank/search?accountNumber=12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("12345"));
    }

    @Test
    void shouldUpdateAccount() throws Exception {
        // Arrange
        BankAccountDTO response = new BankAccountDTO();
        response.setAccountHolder("New Name");

        when(bankAccountService.updateBankAccount(any(Long.class), any())).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/bank/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountHolder\":\"New Name\", \"accountNumber\":\"12345\", \"balance\":200.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountHolder").value("New Name"));
    }

}