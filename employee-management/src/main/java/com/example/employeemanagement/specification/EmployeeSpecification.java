package com.example.employeemanagement.specification;

import com.example.employeemanagement.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class EmployeeSpecification {
    public static Specification<Employee> hasDepartment(String department){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal
                (root.get("department"),department);
    }

    public static Specification<Employee> nameContains(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like
                (criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                );

    }

    public static Specification<Employee> salaryGreaterThan(BigDecimal salary){
        return((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("salary"),
                salary));
    }
}
