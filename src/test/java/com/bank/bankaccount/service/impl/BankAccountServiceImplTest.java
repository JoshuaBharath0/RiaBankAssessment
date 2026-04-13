package com.bank.bankaccount.service.impl;

import com.bank.bankaccount.dto.BankAccountDTO;
import com.bank.bankaccount.entities.BankAccountEntity;
import com.bank.bankaccount.repository.BankAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {
    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @Test
    void testCreateAccount_ShouldFail_WhenAccountAlreadyExists() {
        //Arrange
        BankAccountDTO input = new BankAccountDTO();
        input.setAccountNumber("1234567");

        //pretend the account number exists
        when(bankAccountRepository.findByAccountNumber("1234567"))
                .thenReturn(Optional.of(new BankAccountEntity()));

        //we should expect an error
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            bankAccountService.createAccount(input);
        });
        //Act and assert
        assertTrue((ex.getMessage().contains("already exist")));
        //ensure nothing was saved
        verify(bankAccountRepository, never()).save(any());
    }

    @Test
    void testCreateAccount_ShouldSaveA_WhenAccountIsNew() {

        //Arrange
        BankAccountDTO input = new BankAccountDTO();
        input.setAccountHolder("Bruce Wayne");
        input.setAccountNumber("1234567");
        input.setBalance(new BigDecimal("1000000000000000.00"));

        //pretend account number does not exist
        when(bankAccountRepository.findByAccountNumber("1234567")).thenReturn(Optional.empty());

        // pretend to save and return some kind of data
        BankAccountEntity saveEnity = new BankAccountEntity();
        saveEnity.setId(100L);
        saveEnity.setAccountNumber("1234567");
        when(bankAccountRepository.save(any(BankAccountEntity.class))).thenReturn(saveEnity);

        //Act
        BankAccountDTO result = bankAccountService.createAccount(input);
        //Assert
        assertNotNull(result);
        assertEquals("1234567", result.getAccountNumber());

        verify(bankAccountRepository, times(1)).save(any());
    }
}