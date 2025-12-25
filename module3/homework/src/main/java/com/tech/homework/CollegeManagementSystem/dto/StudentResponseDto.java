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
public class StudentResponseDto {

    private Long id;

    private String name;

    // ManyToMany → list of IDs
    private List<ProfessorSummaryDto> professors = new ArrayList<>();

    // ManyToMany → list of IDs
    private List<SubjectSummaryDto> subjects = new ArrayList<>();
}

