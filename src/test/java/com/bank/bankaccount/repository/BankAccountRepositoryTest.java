package com.bank.bankaccount.repository;

import com.bank.bankaccount.entities.BankAccountEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BankAccountRepositoryTest {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Test
    void testFindByAccountNumber() {
        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        bankAccountEntity.setAccountHolder("Joshua");
        bankAccountEntity.setAccountNumber("12345");
        bankAccountEntity.setBalance(500000.0);
        bankAccountRepository.save(bankAccountEntity);

        Optional<BankAccountEntity> found=bankAccountRepository.findByAccountNumber("12345");
        assertTrue(found.isPresent());
        assertEquals("Joshua",found.get().getAccountHolder());


    }


}