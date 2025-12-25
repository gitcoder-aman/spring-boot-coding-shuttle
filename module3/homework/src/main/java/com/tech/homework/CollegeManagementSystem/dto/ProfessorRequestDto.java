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
public class ProfessorRequestDto {

    private Long id;

    @NotBlank(message = "Professor title can't be empty")
    private String title;

    // OneToMany → list of Subject IDs
    private List<Long> subjectIds = new ArrayList<>();

    // ManyToMany → list of Student IDs
    private List<Long> studentIds = new ArrayList<>();
}

