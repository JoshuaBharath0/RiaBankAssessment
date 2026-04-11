package com.bank.bankaccount.repository;

import com.bank.bankaccount.entities.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccountEntity,Long> {
    Optional<BankAccountEntity> findByAccountNumber(String accountNumber);
}
