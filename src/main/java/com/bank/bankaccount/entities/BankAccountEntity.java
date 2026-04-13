package com.bank.bankaccount.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class BankAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "account holder cannot be empty")
    private String accountHolder;
    //Optimize DB storage for IBANs
    @Column(unique = true,length = 34)
    @NotBlank(message = "Account number is required")
    @Pattern(
            regexp = "^[A-Z0-9]{7,34}$",
            message = "Invalid format: Use 7-12 digits for Domestic (SA) or up to 34 UPPERCASE alphanumeric characters for International (IBAN)."
    )
    private String accountNumber;

    // we use this annotation as you never register with a bank with a negative bank balance
    //made it BigDecimal instead as money could be lost using double during calculations
    @PositiveOrZero(message = "balance must be greater or equal 0")
    private BigDecimal balance;



}
