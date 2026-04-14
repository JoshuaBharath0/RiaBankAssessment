package com.bank.bankaccount.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalException {

    // catches the manual errors eg(id not found in DB).
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleBusinessErrors(RuntimeException ex) {
        return buildResponse(ex.getMessage(), "BUSINESS_LOGIC_ERROR", HttpStatus.BAD_REQUEST);
    }

    // catches errors in the controller for @Valid.
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().iterator().next().getMessage();
        return buildResponse(message, "VIOLATION_ERROR", HttpStatus.BAD_REQUEST);
    }

    // catches errors in the controller for @valid.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleBusinessErrors(MethodArgumentNotValidException ex) {
        //String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        String combinedMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(java.util.stream.Collectors.joining("; "));
        return buildResponse(combinedMessages, "INPUT_FORMAT_ERROR", HttpStatus.BAD_REQUEST);
    }
// safety net for server crashes.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleBusinessErrors(Exception ex) {
        return buildResponse("Server error occurred, try again later", "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR                                                           );
    }

    // helper method to organise structure of error display
    private ResponseEntity<Object> buildResponse(String message, String errorCode, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error_code", errorCode);
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }

}
