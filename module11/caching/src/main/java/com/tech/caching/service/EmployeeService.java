package com.tech.caching.service;

import com.tech.caching.dto.EmployeeDTO;
import com.tech.caching.entity.Employee;
import com.tech.caching.exception.ResourceNotFoundException;
import com.tech.caching.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final String CACHE_NAME="employees";

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }


    @Cacheable(cacheNames = CACHE_NAME,key = "#id")
    public EmployeeDTO getEmployeeId(Long id) {

        Employee employeeEntity = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee id not found"));


        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employeeEntityList = employeeRepository.findAll();
        return employeeEntityList
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    @CachePut(cacheNames = CACHE_NAME,key = "#result.id")
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        Employee toSaveEntity = this.modelMapper.map(employeeDTO, Employee.class);
        Employee saveEmployeeEntity = employeeRepository.save(toSaveEntity);
        return modelMapper.map(saveEmployeeEntity, EmployeeDTO.class);
    }
    public void isExistsByEmployeeId(Long empId) {
        boolean exists = employeeRepository.existsById(empId);
        if (!exists) throw new ResourceNotFoundException("Employee with id " + empId + " does not exist");
    }
    @CachePut(cacheNames = CACHE_NAME,key = "#empId")
    public EmployeeDTO updateEmployeeById(EmployeeDTO employeeDTO, Long empId) {
        isExistsByEmployeeId(empId);
        Employee employeeEntity = this.modelMapper.map(employeeDTO, Employee.class);
        employeeEntity.setId(empId);
        Employee saveEmployeeEntity = employeeRepository.save(employeeEntity);
        return this.modelMapper.map(saveEmployeeEntity, EmployeeDTO.class);
    }

    @CacheEvict(cacheNames = CACHE_NAME,key = "#empId")
    public boolean deleteEmployeeById(Long empId) {
        isExistsByEmployeeId(empId);
        this.employeeRepository.deleteById(empId);
        return true;
    }

    public EmployeeDTO updatePartialEmployeeById(Map<String, Object> updates, Long empId) {
        isExistsByEmployeeId(empId);
        Employee employeeEntity = employeeRepository.findById(empId).orElse(null);
        updates.forEach((field, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findField(Employee.class, field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
        });
        return this.modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }
//    public EmployeeDTO getEmployeeByEmail(String email){
//        EmployeeEntity employeeEntity = employeeRepository.findByEmail(email);
//        return this.modelMapper.map(employeeEntity, EmployeeDTO.class);
//    }
}
