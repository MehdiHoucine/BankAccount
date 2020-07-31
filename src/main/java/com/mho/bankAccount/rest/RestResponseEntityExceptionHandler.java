package com.mho.bankAccount.rest;

import com.mho.bankAccount.domain.exception.InsufficientBalanceException;
import com.mho.bankAccount.domain.exception.InvalidAmountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class, InsufficientBalanceException.class, InvalidAmountException.class})
    protected ResponseEntity<Object> handleConflict(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
