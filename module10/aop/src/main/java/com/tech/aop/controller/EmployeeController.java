package com.tech.aop.controller;

import com.tech.aop.entity.Employee;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @PostMapping
    public String createEmployee(
            @Valid @RequestBody Employee employee) {

        return "Employee created successfully";
    }
}