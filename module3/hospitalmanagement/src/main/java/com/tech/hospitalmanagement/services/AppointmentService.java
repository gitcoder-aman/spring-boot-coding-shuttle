package com.tech.hospitalmanagement.services;

import com.tech.hospitalmanagement.entities.Appointment;
import com.tech.hospitalmanagement.entities.Doctor;
import com.tech.hospitalmanagement.entities.Patient;
import com.tech.hospitalmanagement.repositories.AppointmentRepository;
import com.tech.hospitalmanagement.repositories.DoctorRepository;
import com.tech.hospitalmanagement.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Appointment createANewAppointment(Appointment appointment,Long patientId,Long doctorId){
        Patient patient = patientRepository.findById(patientId).orElseThrow();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        appointmentRepository.save(appointment);
        return appointment;
    }
}
