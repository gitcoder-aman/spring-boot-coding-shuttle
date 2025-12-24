package com.tech.homework.CollegeManagementSystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionRecordDto {

    private Long id;

    @NotNull(message = "Admission fee is required")
    @Positive(message = "Admission fee must be positive")
    private Integer fees;

    @NotNull(message = "Student id is required")
    private Long studentId;
}

