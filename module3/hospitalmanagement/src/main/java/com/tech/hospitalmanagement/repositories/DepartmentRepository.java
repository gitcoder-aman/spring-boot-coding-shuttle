package com.tech.hospitalmanagement.repositories;

import com.tech.hospitalmanagement.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}