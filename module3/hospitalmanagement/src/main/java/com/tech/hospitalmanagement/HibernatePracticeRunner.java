package com.tech.hospitalmanagement;

import com.tech.hospitalmanagement.entities.Patient;
import com.tech.hospitalmanagement.entities.type.BloodGroupType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class HibernatePracticeRunner implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) {

        //Patient  : Now this is Transient state now move into session for Persistent state
        Patient patient = new Patient();
        patient.setName("Aman Kr");
        patient.setEmail("amankr@gmail.com");
        patient.setBloodGroup(BloodGroupType.AB_POSITIVE);
        patient.setGender("Male");
        patient.setBirthDate(LocalDate.of(2002,4,19));
        patient.setCreatedAt(LocalDateTime.now());

        // Transient → Persistent
        entityManager.persist(patient);//now this is patient in Persistent state - session database


        // Update while persistent (WILL be saved)
        patient.setName("Rahul Kumar");


        // FORCE write to DB
        entityManager.flush();

        // Persistent → Detached
        entityManager.detach(patient);

        // Update after detach (WILL NOT be saved)
        patient.setName("Sachin Kumar");

        //  Detached -> REMOVED (must reattach first)
        Patient managedPatient =
                entityManager.merge(patient);  // Detached → Persistent

        entityManager.remove(managedPatient);
    }
}
