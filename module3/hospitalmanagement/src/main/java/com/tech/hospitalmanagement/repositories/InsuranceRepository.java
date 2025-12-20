package com.tech.hospitalmanagement.repositories;

import com.tech.hospitalmanagement.entities.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}