package com.tech.testing.Testing.service;

import com.tech.testing.Testing.TestContainerConfiguration;
import com.tech.testing.Testing.dto.EmployeeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@Import(TestContainerConfiguration.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void getEmployeeById_WhenEmployeeIdIsPresent_ThenReturnEmployeeDto(){
        EmployeeDTO employeeDTO = employeeService.getEmployeeId(1L);
    }
}