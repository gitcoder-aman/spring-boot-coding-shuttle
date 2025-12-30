package com.tech.Module4.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDto {

    private Long id;

    private String name;
    private String email;
    private Integer age;

    private String role; //ADMIN,ROLE
    private Double salary;

    private LocalDate dateOfJoining;
    private Boolean isActive;
}
