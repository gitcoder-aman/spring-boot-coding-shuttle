package com.tech.testing.Testing.repository;

import com.tech.testing.Testing.TestContainerConfiguration;
import com.tech.testing.Testing.entities.EmployeeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Import(TestContainerConfiguration.class)
//@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class EmployeeRepositoryTest {


    @Autowired
    private EmployeeRepository employeeRepository;
    private EmployeeEntity employee;

    @BeforeEach
    void setUp(){
        employee = EmployeeEntity.builder()
                .email("aman@gmail.com")
                .age(24)
                .name("Aman Kumar")
                .salary(23434.0)
                .build();
    }

    @Test
    void testFindByEmail_whenEmailIsPresent_thenReturnEmployee() {

        //Arrange,Given
        employeeRepository.save(employee);

        //Act When
       List<EmployeeEntity>employeeList =  employeeRepository.findByEmail(employee.getEmail());

       //Assert,Then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isNotEmpty();
        assertThat(employeeList.getFirst().getEmail())
                .isEqualTo(employee.getEmail());

    }

    @Test
    void testFindByEmail_whenEmailIsNotFound_thenReturnEmptyEmployeeList(){

        //Given
        String email = "notPresent123@gmail.com";
        List<EmployeeEntity>employeeList =  employeeRepository.findByEmail(email);
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isEmpty();

        //When

        //Then
    }
}