package com.tech.module2.dto;

import com.tech.module2.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "Name of Employee cannot be empty")
    @Size(min = 3,max=20,message = "Number of Characters in name should be in range:[3,10]")
    private String name;
    @Email(message = "Email should be valid email")
    private String email;

    @NotNull(message = "Age of the Employee can not be blank")
    @Min(value = 18,message = "Age cannot be lower than 18")
    @Max(value = 50,message = "Age cannot be higher than 50")
    private Integer age;

    @NotBlank(message ="Role of the employee can not be blank")
//    @Pattern(regexp = "^(ADMIN|USER)$",message = "Employee can be either User and Admin")
    @EmployeeRoleValidation    //own validator makes
    private String role; //ADMIN,ROLE

    @NotNull(message = "Salary of Employee can not be empty")
    @Positive(message = "Employee salary should be not null")
    @Digits(integer = 6,fraction = 2,message = "The salary can not be in the xxxxx.yy")
    private Double salary;

    @PastOrPresent(message = "Date of Joining field employee can not be in future")
    private LocalDate dateOfJoining;
    private Boolean isActive;
}
