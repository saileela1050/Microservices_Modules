package com.example.employeemanagement.dto;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class EmployeeSearchResponse {
    private Long id;
    private String name;
    private String email;
    private BigDecimal salary;
    private String department;

    public EmployeeSearchResponse(Long id, String name, String email, BigDecimal salary, String department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.department = department;
    }
}
