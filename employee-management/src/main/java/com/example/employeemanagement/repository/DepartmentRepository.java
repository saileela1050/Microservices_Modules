package com.example.employeemanagement.repository;

import com.example.employeemanagement.entity.Department;
import com.example.employeemanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department,Long> {

@Query("""
SELECT DISTINCT d FROM Department d LEFT JOIN FETCH d.employees""")
    List<Department> findAllWithEmployees();
}
