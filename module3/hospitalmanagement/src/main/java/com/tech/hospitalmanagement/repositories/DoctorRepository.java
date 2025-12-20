package com.tech.hospitalmanagement.repositories;

import com.tech.hospitalmanagement.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}