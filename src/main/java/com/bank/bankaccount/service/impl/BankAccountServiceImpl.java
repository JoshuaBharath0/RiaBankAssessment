package com.bank.bankaccount.service.impl;

import com.bank.bankaccount.dto.BankAccountDTO;
import com.bank.bankaccount.entities.BankAccountEntity;
import com.bank.bankaccount.repository.BankAccountRepository;
import com.bank.bankaccount.service.BankAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * Service Implementation for Bank Accounts.
 * Note: I am using a Global Exception Handler instead of try-catch blocks.
 * This keeps the code clean and ensures all errors return a neat JSON
 * message to the user automatically.
 */

// Enforce read-only to prevent accidental side effect and data integrity
@Transactional(readOnly=true)
@Service
public class BankAccountServiceImpl implements BankAccountService {
    final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Transactional
    @Override
    public BankAccountDTO createAccount(BankAccountDTO bankAccountDTO) {


            if(bankAccountRepository.findByAccountNumber(bankAccountDTO.getAccountNumber()).isPresent()){
                throw new RuntimeException("Account failed as account: "+bankAccountDTO.getAccountNumber()+" already exists");
            }
            BankAccountEntity bankAccountEntity=new BankAccountEntity();
            bankAccountEntity.setAccountHolder(bankAccountDTO.getAccountHolder());
            bankAccountEntity.setAccountNumber(bankAccountDTO.getAccountNumber());
            bankAccountEntity.setBalance(bankAccountDTO.getBalance());

            BankAccountEntity saveEntity=bankAccountRepository.save(bankAccountEntity);
            return maptoDTO(saveEntity);
       
    }

    @Override
    public BankAccountDTO getBankAccountById(long id) {

          BankAccountEntity bankAccountEntity=bankAccountRepository.findById(id)
                  .orElseThrow(()-> new RuntimeException("Account could not be found with ID: "+id));
          return maptoDTO(bankAccountEntity);
    }

    @Override
    public BankAccountDTO getBankAccountByAccountNumber(String accountNumber) {

            BankAccountEntity bankAccountEntity=bankAccountRepository.findByAccountNumber(accountNumber)
                    .orElseThrow(()-> new RuntimeException("Account could not be found with account number: "+accountNumber));
            return maptoDTO(bankAccountEntity);

    }


@Transactional
    @Override
    public BankAccountDTO updateBankAccount(Long id, BankAccountDTO bankAccountDTO) {


            BankAccountEntity existingBankAccountEntity=bankAccountRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("Update has failed, user could not be found with ID: "+id));

            existingBankAccountEntity.setAccountHolder(bankAccountDTO.getAccountHolder());
            existingBankAccountEntity.setBalance(bankAccountDTO.getBalance());

            BankAccountEntity updateEntity=bankAccountRepository.save(existingBankAccountEntity);
            return maptoDTO(updateEntity);


    }

    private BankAccountDTO maptoDTO(BankAccountEntity bankAccountEntity){
        BankAccountDTO bankAccountDTO=new BankAccountDTO();
        bankAccountDTO.setId(bankAccountEntity.getId());
        bankAccountDTO.setAccountHolder(bankAccountEntity.getAccountHolder());
        bankAccountDTO.setAccountNumber(bankAccountEntity.getAccountNumber());
        bankAccountDTO.setBalance(bankAccountEntity.getBalance());
        return bankAccountDTO;
    }
}
