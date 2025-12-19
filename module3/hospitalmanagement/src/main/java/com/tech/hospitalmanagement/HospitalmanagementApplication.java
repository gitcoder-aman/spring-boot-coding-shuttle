package com.tech.hospitalmanagement;

import com.tech.hospitalmanagement.entities.Patient;
import com.tech.hospitalmanagement.entities.type.BloodGroupType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HospitalmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalmanagementApplication.class, args);

		//Practical of Hibernate Object States:
		//Transient
		//Persistent
		//Detached
		//Removed
/*		SessionFactory sf = new Configuration().buildSessionFactory();

		//Creating Patient object
		Patient patient = new Patient();
		patient.setId(1414L);
		patient.setName("Aman Kr");
		patient.setEmail("amankr@gmail.com");
		patient.setBloodGroup(BloodGroupType.AB_POSITIVE);
		patient.setGender("Male");
		patient.setBirthDate(LocalDate.of(2002,4,19));
		patient.setCreatedAt(LocalDateTime.now());
		//Patient  : Now this is Transient state now move into session for Persistent state

		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		session.persist(patient);  //now this is patient in Persistent state - session database
		tx.commit();


		sf.close();*/


	}

}
