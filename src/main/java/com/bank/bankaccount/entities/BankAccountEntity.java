package com.bank.bankaccount.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Entity
@Data
public class BankAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "account holder cannot be empty")
    private String accountHolder;

    @Column(unique = true)
    @NotBlank(message = "Account number is required")
    private String accountNumber;

    // we use this annotation as you never register with a bank with a negative bank balance
    @PositiveOrZero(message = "balance must be greater or equal 0")
    private Double balance;



}
