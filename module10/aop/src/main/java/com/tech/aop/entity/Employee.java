package com.tech.aop.entity;

import com.tech.aop.annotation.ValidEmployee;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {

    @ValidEmployee
    private String employeeId;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email")
    private String email;

    @Min(value = 18, message = "Age must be at least 18")
    private int age;
}
