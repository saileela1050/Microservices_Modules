package com.example.employeemanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class EmployeePatchRequest {

    private String name;

    @Email(message = "email required valid format")
    private String email;

    @Positive(message = "salary is greater than Zero")
    private BigDecimal salary;

    //private String department;
    private Long departmentid;


    private LocalDate joiningDate;

    public EmployeePatchRequest() {
    }
}
