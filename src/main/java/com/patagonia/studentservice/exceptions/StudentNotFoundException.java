package com.patagonia.studentservice.exceptions;

public class StudentNotFoundException extends RuntimeException{

    public StudentNotFoundException(Long id){
        super("Not found. ID: " + id);
    }
}
