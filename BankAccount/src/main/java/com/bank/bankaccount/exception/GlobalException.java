package com.bank.bankaccount.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalException {
    public ResponseEntity<Object>handleRuntimeException(RuntimeException ex){

    }
}
