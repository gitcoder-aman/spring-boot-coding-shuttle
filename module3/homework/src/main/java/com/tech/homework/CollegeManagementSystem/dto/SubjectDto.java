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
public class SubjectDto {

    private Long id;

    @NotBlank(message = "Subject title can't be empty")
    private String title;

    // ManyToOne → single ID
    @NotNull(message = "Professor id is required")
    private Long professorId;

    // ManyToMany → list of IDs
    private List<Long> studentIds = new ArrayList<>();
}

