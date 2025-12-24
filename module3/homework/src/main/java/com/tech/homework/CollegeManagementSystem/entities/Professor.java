package com.tech.homework.CollegeManagementSystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "professor_title",nullable = false,length = 35)
    private String title;

    @OneToMany(mappedBy = "professor", fetch = FetchType.LAZY)
    private List<Subject> subject = new ArrayList<>();  //this is inverse side

    @ManyToMany(mappedBy = "professors")
    private List<Student> students = new ArrayList<>(); // this is inverse side

}
