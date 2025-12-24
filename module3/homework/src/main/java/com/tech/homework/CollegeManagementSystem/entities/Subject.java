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
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_title",length = 50,nullable = false)
    private String title;

    @ManyToOne
    private Professor professor;   // this is owning side

    @ManyToMany(mappedBy = "subjects")
    private List<Student>students = new ArrayList<>();  //this is inverse side
}
