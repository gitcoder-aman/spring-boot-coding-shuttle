package com.tech.homework.CollegeManagementSystem.dto;

import com.tech.homework.CollegeManagementSystem.entities.Student;
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
public class AdmissionRecordResponseDto {

    private Long id;

    private Integer fees;

    private StudentSummaryDto student;
}

