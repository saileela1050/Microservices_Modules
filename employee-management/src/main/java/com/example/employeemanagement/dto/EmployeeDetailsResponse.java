package com.example.employeemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetailsResponse {
    private Long id;
    private String name;
    private String email;
    private BigDecimal salary;
    private String department;
    private LocalDate joiningDate;
    private DepartmentResp departmentDetails;


    public EmployeeDetailsResponse(Long id, String name,
                                   String email, BigDecimal salary,
                                   Long departmenId, LocalDate joiningDate, DepartmentResp departmentResp) {
    }
}
