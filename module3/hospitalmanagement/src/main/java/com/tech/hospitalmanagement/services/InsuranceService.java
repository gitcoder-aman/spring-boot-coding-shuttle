package com.tech.hospitalmanagement.services;

import com.tech.hospitalmanagement.entities.Insurance;
import com.tech.hospitalmanagement.entities.Patient;
import com.tech.hospitalmanagement.repositories.InsuranceRepository;
import com.tech.hospitalmanagement.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Insurance assignInsuranceToPatient(Insurance insurance, Long patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow();
        patient.setInsurance(insurance);  //dirty patient

        insurance.setPatient(patient);
        return insurance;
    }
}
