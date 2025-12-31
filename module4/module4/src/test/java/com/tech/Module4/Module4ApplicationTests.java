package com.tech.Module4;

import com.tech.Module4.advice.ApiResponse;
import com.tech.Module4.clients.EmployeeClient;
import com.tech.Module4.dto.EmployeeDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Module4ApplicationTests {

	@Autowired
	private EmployeeClient employeeClient;

	@Test
	void contextLoads() {
	}

	@Test
	@Order(3)
	void getAllEmployee(){
		List<EmployeeDto>employeeDtoList = employeeClient.getAllEmployees();
		System.out.println(employeeDtoList);
	}

	@Test
	@Order(2)
	void getEmployeeByIdTest(){
		EmployeeDto employeeDto = employeeClient.getEmployeeById(1354L);
		System.out.println(employeeDto.getEmail());
	}

	@Test
	@Order(1)
	void createNewEmployeeTest(){

		EmployeeDto employeeDto = new EmployeeDto(null,"Anuj","anuj@gmail.com",2,"USER",5000.0, LocalDate.of(2020,12,1),true);
		ResponseEntity<ApiResponse<EmployeeDto>> savedEmployeeDto = employeeClient.createNewEmployee(employeeDto);
		System.out.println(savedEmployeeDto);
	}

}
