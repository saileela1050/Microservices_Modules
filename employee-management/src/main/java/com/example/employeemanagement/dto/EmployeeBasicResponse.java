package com.example.employeemanagement.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class EmployeeBasicResponse {
    private Long id;
    private String name;
    private BigDecimal salary;


    public EmployeeBasicResponse(Long id, String name, BigDecimal salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }



}
