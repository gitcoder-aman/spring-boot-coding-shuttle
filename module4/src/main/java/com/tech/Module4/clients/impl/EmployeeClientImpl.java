package com.tech.Module4.clients.impl;

import com.tech.Module4.advice.ApiResponse;
import com.tech.Module4.clients.EmployeeClient;
import com.tech.Module4.dto.EmployeeDto;
import com.tech.Module4.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;

    Logger log = LoggerFactory.getLogger(EmployeeClientImpl.class);


    @Override
    public List<EmployeeDto> getAllEmployees() {

//        log.error("error Log");
//        log.warn("warn Log");
//        log.info("info Log");
//        log.debug("debug Log");
//        log.trace("trace Log");

        log.trace("Trying to retrieve all employee in getAllEmployees");
        try {
            log.info("Attempting to call the restClient Method in getAllEmployees:");

            List<EmployeeDto>employeeDtoList = restClient.get()
                    .uri("employee")
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,(req,res)->{
                        log.error(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("Could not find employee");
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });
            log.debug("Successfully retrieved the employees in getAllEmployees");
            log.trace("Retrieved employees list in getAllEmployees : {}",employeeDtoList);
            assert employeeDtoList != null;
            return employeeDtoList;
        } catch (Exception e) {
            log.error("Exception occurred in getAllEmployees",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDto getEmployeeById(Long empId) {

        log.trace("Trying to get employee by id in getEmployeeById  {}",empId);

        try {
            EmployeeDto employeeDtoApiResponse = restClient.get()
                    .uri("employee/{empId}",empId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            assert employeeDtoApiResponse != null;
            return employeeDtoApiResponse;
        }catch (Exception e){
            log.error("Exception occurred in getEmployeeById",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<EmployeeDto>> createNewEmployee(EmployeeDto employeeDto) {

        log.trace("Trying to create employee with information {}",employeeDto);
        try {
            ResponseEntity<ApiResponse<EmployeeDto>> response = restClient.post()
                    .uri("employee")
                    .body(employeeDto)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,(req,res)->{
                        log.debug("4xxClient error occurred during create An employee");
                        log.error(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("Could not create employee");
                    })
                    .toEntity(new ParameterizedTypeReference<>() {
                    });
            log.trace("Successfully create a new employee :{}",response.getBody());
            return response;
        }catch (Exception e){
            log.error("Exception occurred in createNewEmployee",e);
            throw new RuntimeException(e);
        }
    }
}
