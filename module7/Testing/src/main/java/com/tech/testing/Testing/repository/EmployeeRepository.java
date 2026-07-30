package com.tech.testing.Testing.repository;

import com.tech.testing.Testing.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long> {

//    List<EmployeeEntity>findByName(String name);

    List<EmployeeEntity> findByEmail(String email);
}
