package com.example.employeemanagement.exception;

public class ResourceNotfoundException extends RuntimeException{
    public ResourceNotfoundException(String message){
        super(message);
    }
}
