package com.example.employeemanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class EmployeeUpdateRequest {

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "email required valid format")
    private String email;

    @NotNull(message = " salary is required")
    @Positive(message = "salary is greater than Zero")
    private BigDecimal salary;

    @NotNull(message = "department id is required")
    private Long departmentId;

   // private String department;

    @NotNull(message = "joinindate is reuired")
    private LocalDate joiningDate;

    public EmployeeUpdateRequest() {
    }
}
