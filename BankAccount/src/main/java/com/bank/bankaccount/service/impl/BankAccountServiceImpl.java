package com.bank.bankaccount.service.impl;

import com.bank.bankaccount.dto.BankAccountDTO;
import com.bank.bankaccount.entities.BankAccountEntity;
import com.bank.bankaccount.repository.BankAccountRepository;
import com.bank.bankaccount.service.BankAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Enforce read-only to prevent accidental side effect and data integrity
@Transactional(readOnly=true)
@Service
public class BankAccountServiceImpl implements BankAccountService {
    private BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Transactional
    @Override
    public BankAccountDTO createAccount(BankAccountDTO bankAccountDTO) {


            if(bankAccountRepository.findByAccountNumber(bankAccountDTO.getAccountNumber()).isPresent()){
                throw new RuntimeException("Account failed as accound: "+bankAccountDTO.getAccountNumber()+" already exists");
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
      try{
          BankAccountEntity bankAccountEntity=bankAccountRepository.findById(id)
                  .orElseThrow(()-> new RuntimeException("Account could not be found with ID: "+id));
          return maptoDTO(bankAccountEntity);
      }catch (Exception e){
          throw new RuntimeException("Server Error: Retrieval failed. "+e.getMessage());
      }
    }

    @Override
    public BankAccountDTO getBankAccountByAccountNumber(String accountNumber) {
        try{
            BankAccountEntity bankAccountEntity=bankAccountRepository.findByAccountNumber(accountNumber)
                    .orElseThrow(()-> new RuntimeException("Account could not be found with account number: "+accountNumber));
            return maptoDTO(bankAccountEntity);
        }catch (Exception e){
            throw new RuntimeException("Server Error: Retrieval failed. "+e.getMessage());
        }
    }


@Transactional
    @Override
    public BankAccountDTO updateBankAccount(Long id, BankAccountDTO bankAccountDTO) {

        try {
            BankAccountEntity existingBankAccountEntity=bankAccountRepository.findById(id)
                    .orElseThrow(()-> new RuntimeException("Update has failed, user could not be found with ID: "+id));

            existingBankAccountEntity.setAccountHolder(bankAccountDTO.getAccountHolder());
            existingBankAccountEntity.setBalance(bankAccountDTO.getBalance());

            BankAccountEntity updateEntity=bankAccountRepository.save(existingBankAccountEntity);
            return maptoDTO(updateEntity);
        }catch(Exception e){
            throw new RuntimeException("Server Error: could not update bank account: "+e.getMessage());
        }

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
