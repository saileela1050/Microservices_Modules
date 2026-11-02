package com.example.employeemanagement.dto;

import com.example.employeemanagement.entity.Department;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@ToString
public class EmployeeCreateRequest {

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message="Invalid email format")
    private String email;

    @NotNull(message = "salary is required")
    @Positive(message = "salary must be greater than zero")
    private BigDecimal salary;

    @NotNull(message = "department id is not null")
    //private String department;
    private Long departmentId;

    @NotNull(message = "Joiningdate is required")
    private LocalDate joiningDate;


    public EmployeeCreateRequest() {
    }
}
