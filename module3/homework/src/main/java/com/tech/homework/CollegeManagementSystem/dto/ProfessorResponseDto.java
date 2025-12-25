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
public class ProfessorResponseDto {

    private Long id;

    private String title;

    // OneToMany → list of Subject IDs
    private List<SubjectSummaryDto> subjects = new ArrayList<>();

    // ManyToMany → list of Student IDs
    private List<StudentSummaryDto> students = new ArrayList<>();
}

