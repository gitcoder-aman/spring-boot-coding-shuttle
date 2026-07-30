package com.tech.caching.repository;

import com.tech.caching.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

//    List<EmployeeEntity>findByName(String name);

    List<Employee> findByEmail(String email);
}