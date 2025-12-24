package com.tech.homework.CollegeManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tech.homework.CollegeManagementSystem.entities.Student;
import com.tech.homework.CollegeManagementSystem.entities.Subject;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDto {

    private Long id;

    @NotBlank(message = "Professor title can't be empty")
    private String title;

    // OneToMany → list of Subject IDs
    private List<Long> subjectIds = new ArrayList<>();

    // ManyToMany → list of Student IDs
    private List<Long> studentIds = new ArrayList<>();
}

