package com.tech.homework.CollegeManagementSystem.repositories;

import com.tech.homework.CollegeManagementSystem.entities.AdmissionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdmissionRecordRepository extends JpaRepository<AdmissionRecord, Long> {
}