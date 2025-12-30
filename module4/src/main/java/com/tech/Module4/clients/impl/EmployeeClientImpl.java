package com.tech.Module4.clients.impl;

import com.tech.Module4.advice.ApiResponse;
import com.tech.Module4.clients.EmployeeClient;
import com.tech.Module4.dto.EmployeeDto;
import com.tech.Module4.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;

    @Override
    public List<EmployeeDto> getAllEmployees() {

        try {
            List<EmployeeDto>employeeDtoList = restClient.get()
                    .uri("employee")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            assert employeeDtoList != null;
            return employeeDtoList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDto getEmployeeById(Long empId) {
        try {
            EmployeeDto employeeDtoApiResponse = restClient.get()
                    .uri("employee/{empId}",empId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            assert employeeDtoApiResponse != null;
            return employeeDtoApiResponse;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<EmployeeDto>> createNewEmployee(EmployeeDto employeeDto) {
        try {
            ResponseEntity<ApiResponse<EmployeeDto>> response = restClient.post()
                    .uri("employee")
                    .body(employeeDto)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,(req,res)->{
                        System.out.println(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("Could not create employee");
                    })
                    .toEntity(new ParameterizedTypeReference<>() {
                    });
            return response;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
