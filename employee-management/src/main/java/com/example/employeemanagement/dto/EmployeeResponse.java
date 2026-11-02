package com.example.employeemanagement.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@ToString
public class EmployeeResponse {
    private Long id;
    private String name;
    private String email;
    private BigDecimal salary;
    private String department;
    private LocalDate joiningDate;

    public EmployeeResponse(Long id, String name, String email, BigDecimal salary, String department, LocalDate joiningDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.department = department;
        this.joiningDate = joiningDate;
    }

    public EmployeeResponse() {
    }


}
