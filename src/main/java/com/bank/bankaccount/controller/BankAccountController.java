package com.bank.bankaccount.controller;

import com.bank.bankaccount.dto.BankAccountDTO;
import com.bank.bankaccount.service.BankAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class BankAccountController {
    private BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    // The path will be pointing to "/bank" due to RESTful standard and since there is only one post method this won't cause Ambiguity.
    @PostMapping()
    public ResponseEntity<BankAccountDTO> createBankAccount(@Valid @RequestBody BankAccountDTO bankAccountDTO) {
        BankAccountDTO createdAccount = bankAccountService.createAccount(bankAccountDTO);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<BankAccountDTO> getByBankAccountNumber(@RequestParam String accountNumber){
        BankAccountDTO findAccount=bankAccountService.getBankAccountByAccountNumber(accountNumber);
        return ResponseEntity.ok(findAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountDTO> getByBankIdNumber(@PathVariable long id){
        BankAccountDTO findAccount=bankAccountService.getBankAccountById(id);
        return ResponseEntity.ok(findAccount);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BankAccountDTO> updateBankAccount(@Valid @PathVariable long id,@RequestBody BankAccountDTO bankAccountDTO){
        BankAccountDTO updateAccount=bankAccountService.updateBankAccount(id,bankAccountDTO);
        return ResponseEntity.ok(updateAccount);
    }
}
