package com.example.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private BigDecimal salary;
    //private String department;
    @ManyToOne
    @JoinColumn(name="department_id")
    private Department department;

    private Long departmenId;

    private LocalDate joiningDate;
    //private String phoneNumber;

}
