package com.tech.hospitalmanagement;

import com.tech.hospitalmanagement.dto.BloodGroupStats;
import com.tech.hospitalmanagement.dto.CPatientInfo;
import com.tech.hospitalmanagement.dto.IPatientInfo;
import com.tech.hospitalmanagement.entities.Patient;
import com.tech.hospitalmanagement.repositories.PatientRepository;
import com.tech.hospitalmanagement.services.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @Test
    public void testPatient(){
//        List<Patient>patientList = patientRepository.findAll();
//        List<IPatientInfo>patientList = patientRepository.getAllPatientsInfo();
//        List<CPatientInfo>patientList = patientRepository.getAllPatientsInfoConcrete();
//        List<BloodGroupStats>patientList = patientRepository.getBloodGroupStats();

//        for (IPatientInfo p : patientList) {
//            System.out.println(
//                    p.getId() + " | " + p.getName() + " | " + p.getEmail()
//            );
//        }
//        for (BloodGroupStats p : patientList) {
//            System.out.println(p);
//        }

//        int rowAffected = patientRepository.updatePatientNameWithId("Anuj Sharma",1L);
//        System.out.println(rowAffected);
//
//        patientService.testPatientTransaction();

        //Fetch Eager and Lazy
        List<Patient> patients = patientRepository.getAllPatientWithAppointment();
        for (Patient p : patients){
            System.out.println(p);
        }
    }

    @Test
    public void testDeletePatientInsurance(){
//        patientService.deletePatient(1L);
    }
}
