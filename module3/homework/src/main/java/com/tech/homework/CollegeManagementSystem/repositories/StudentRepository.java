package com.tech.homework.CollegeManagementSystem.repositories;

import com.tech.homework.CollegeManagementSystem.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
}
