package com.tech.hospitalmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @Column(length = 500)
    private String reason;

    @ManyToOne
    @JoinColumn(nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private Patient patient;  // owning side

    @ManyToOne
    @JoinColumn(nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private Doctor doctor;
}
