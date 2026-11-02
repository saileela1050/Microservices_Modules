package com.example.employeemanagement.exception;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

@Getter
public class ErrorResponse {
    private int Status;
    private String Message;
    private LocalDate Timestamp;
    private Map<String,String> errors;

    public ErrorResponse(int status, String message, LocalDate timestamp, Map<String, String> errors) {
        Status = status;
        Message = message;
        Timestamp = timestamp;
        this.errors = errors;
    }

    public ErrorResponse(int status, String message, LocalDate timestamp) {
        Status = status;
       Message = message;
      Timestamp = timestamp;
    }
}
