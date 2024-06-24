package com.senai.back.advice;

import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler({NoSuchElementException.class, EntityNotFoundException.class})
    public ResponseEntity treatNotFound(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity treatBadRequest(ConstraintViolationException exception){
        // var messages = exception.getConstraintViolations()
        //                         .stream()
        //                         .map(violation ->  violation.getMessageTemplate())
        //                         .toList();
        return ResponseEntity.badRequest().body(exception.getMessage().toString());
    }
}

