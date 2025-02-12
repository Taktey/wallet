package com.example.wallet.controller;

import com.example.wallet.exception.InsufficientFundsException;
import com.example.wallet.exception.NoSuchWalletFoundException;
import com.example.wallet.exception.UnknownTransactionTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> handleNoSuchWalletFoundException(NoSuchWalletFoundException e){
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleInsufficientFundsException(InsufficientFundsException e){
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleInvalidJson(HttpMessageNotReadableException e) {
        Throwable cause = e.getCause();

        if (cause instanceof UnknownTransactionTypeException) {
            return new ResponseEntity<>(cause.getMessage(), HttpStatus.BAD_REQUEST);
        }

        log.error(e.getMessage(), e);
        return new ResponseEntity<>("Невалидный JSON в запросе", HttpStatus.BAD_REQUEST);
    }


    /*@ExceptionHandler
    public ResponseEntity<String> handleUnknownTransactionTypeException(UnknownTransactionTypeException e){
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleInvalidJson(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>("Невалидный JSON в запросе", HttpStatus.BAD_REQUEST);
    }*/
}
