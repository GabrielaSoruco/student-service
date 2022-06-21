package com.patagonia.studentservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerException {

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorMessage> studentNotFound(StudentNotFoundException ex){
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
