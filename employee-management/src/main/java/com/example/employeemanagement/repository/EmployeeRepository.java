package com.example.employeemanagement.repository;

import com.example.employeemanagement.dto.EmployeeBasicResponse;
import com.example.employeemanagement.dto.EmployeeSearchResponse;
import com.example.employeemanagement.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    List<Employee> findByDepartment(String department);
    List<Employee> findByNameContainingIgnoreCase(String name);
    List<Employee> findBySalaryGreaterThanEqual(BigDecimal salary);
//
//    @Query("""
//SELECT new com.example.employeemanagement.dto.EmployeeBasicResponse(e.id,e.name,e.salary) FROM Employee e""")
//    List<EmployeeBasicResponse> findBasicEmployeeDetails();


    @Query("""
SELECT new com.example.employeemanagement.dto.EmployeeBasicResponse(e.id,e.name,e.salary) FROM Employee e""")
    List<EmployeeBasicResponse> findBasicEmployeesByDepartment(String department);

    @Query("""
       SELECT new com.example.employeemanagement.dto.EmployeeSearchResponse(
           e.id, e.name, e.email,e.salary,e.department.name)
       FROM Employee e
       """)
    Page<EmployeeSearchResponse> searchEmployees(Pageable pageable);

}
