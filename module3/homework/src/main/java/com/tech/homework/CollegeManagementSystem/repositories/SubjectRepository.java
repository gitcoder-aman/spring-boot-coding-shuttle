package com.tech.homework.CollegeManagementSystem.repositories;

import com.tech.homework.CollegeManagementSystem.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}