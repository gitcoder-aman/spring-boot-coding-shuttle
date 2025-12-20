package com.tech.hospitalmanagement.services;

import com.tech.hospitalmanagement.entities.Patient;
import com.tech.hospitalmanagement.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    @Transactional
    public void deletePatient(Long patientId){
        patientRepository.findById(patientId).orElseThrow();
        patientRepository.deleteById(patientId);
    }
}
