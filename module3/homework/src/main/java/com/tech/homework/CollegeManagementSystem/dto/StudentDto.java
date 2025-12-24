package com.tech.homework.CollegeManagementSystem.dto;

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
public class StudentDto {

    private Long id;

    @NotBlank(message = "Student name can't be empty")
    private String name;

    // ManyToMany → list of IDs
    private List<Long> professorIds = new ArrayList<>();

    // ManyToMany → list of IDs
    private List<Long> subjectIds = new ArrayList<>();
}

