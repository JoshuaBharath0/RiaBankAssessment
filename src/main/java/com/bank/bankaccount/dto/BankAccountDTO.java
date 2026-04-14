package com.bank.bankaccount.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankAccountDTO {


    private Long id;

    @NotBlank(message = "account holder cannot be empty")
    private String accountHolder;


    @Column(unique = true,length = 34) //Optimize DB storage for IBANs
    @NotBlank(message = "Account number is required")
    @Pattern(
            regexp = "^[A-Z0-9]{7,34}$",
            message = "Invalid format: Use 7-12 digits for Domestic (SA) or up to 34 UPPERCASE alphanumeric characters for International (IBAN)."
    )
    private String accountNumber;

    // we use this annotation as you never register with a bank with a negative bank balance
    @Min(value = 0 ,message = "Balance cannot be negative")
    private BigDecimal balance;
}
