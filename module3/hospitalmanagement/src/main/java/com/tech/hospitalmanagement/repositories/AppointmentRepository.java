package com.tech.hospitalmanagement.repositories;

import com.tech.hospitalmanagement.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}