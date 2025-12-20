package com.tech.hospitalmanagement.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    private String name;

    @Column(length = 100)
    private String specialization;

    @Column(nullable = false,unique = true,length = 100)
    private String email;

    @OneToMany(mappedBy = "doctor")
    private Set<Appointment>appointments = new HashSet<>();

}
