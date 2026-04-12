package com.bank.bankaccount.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BankAccountDTO {

    private Long id;

    @NotBlank(message = "account holder cannot be empty")
    private String accountHolder;


    @NotBlank(message = "Account number is required")
    private String accountNumber;

    // we use this annotation as you never register with a bank with a negative bank balance
    @Min(value = 0 ,message = "Balance cannot be negative")
    private Double balance;
}
