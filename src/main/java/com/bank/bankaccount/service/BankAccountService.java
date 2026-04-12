package com.bank.bankaccount.service;

import com.bank.bankaccount.dto.BankAccountDTO;

public interface BankAccountService {
    BankAccountDTO createAccount(BankAccountDTO bankAccountDTO);
    BankAccountDTO getBankAccountById(long id);
    BankAccountDTO getBankAccountByAccountNumber(String accountNumber);
    BankAccountDTO updateBankAccount(Long id,BankAccountDTO bankAccountDTO);
}
