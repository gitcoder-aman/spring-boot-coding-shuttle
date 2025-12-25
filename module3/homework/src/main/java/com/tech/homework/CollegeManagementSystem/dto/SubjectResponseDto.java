package com.tech.homework.CollegeManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class SubjectResponseDto {

    private Long id;

    private String title;

    // ManyToOne → single ID
    private Long professorId;

    // ManyToMany → list of IDs
    private List<StudentSummaryDto> students = new ArrayList<>();
}

