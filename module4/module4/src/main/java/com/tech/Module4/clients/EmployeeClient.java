package com.tech.Module4.clients;

import com.tech.Module4.advice.ApiResponse;
import com.tech.Module4.dto.EmployeeDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeClient {

    List<EmployeeDto> getAllEmployees();

    EmployeeDto getEmployeeById(Long empId);

    ResponseEntity<ApiResponse<EmployeeDto>> createNewEmployee(EmployeeDto employeeDto);
}
