package com.example.employeemanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeSummaryResponse {
    private Long id;
    private String name;


    public EmployeeSummaryResponse() {
    }

    public EmployeeSummaryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
