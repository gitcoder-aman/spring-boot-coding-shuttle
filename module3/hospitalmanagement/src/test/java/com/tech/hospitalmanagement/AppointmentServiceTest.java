package com.tech.hospitalmanagement;

import com.tech.hospitalmanagement.entities.Appointment;
import com.tech.hospitalmanagement.services.AppointmentService;
import com.tech.hospitalmanagement.services.InsuranceService;
import com.tech.hospitalmanagement.services.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class AppointmentServiceTest {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

    @Test
    public void testCreateAppointment(){
        Appointment appointment = Appointment
                .builder()
                .appointmentTime(LocalDateTime.of(2025,11,1,14,0,0,0))
                .reason("Cancer")
                .build();

        var updatedAppointment = appointmentService.createANewAppointment(appointment,1L,2L);
        System.out.println(updatedAppointment);

        patientService.deletePatient(1L);
    }
}
