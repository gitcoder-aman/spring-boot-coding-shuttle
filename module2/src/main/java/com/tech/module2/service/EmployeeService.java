package com.tech.module2.service;

import com.tech.module2.dto.EmployeeDTO;
import com.tech.module2.entities.EmployeeEntity;
import com.tech.module2.exceptions.ResourceNotFoundException;
import com.tech.module2.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;


    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }


    public Optional<EmployeeDTO> getEmployeeId(Long id) {
//        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
//        return employeeEntity.map(employeeEntity1 ->
//            modelMapper.map(employeeEntity1,EmployeeDTO.class)
//        );

        return employeeRepository.findById(id).map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntityList = employeeRepository.findAll();
        return employeeEntityList
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        EmployeeEntity toSaveEntity = this.modelMapper.map(employeeDTO, EmployeeEntity.class);
        EmployeeEntity saveEmployeeEntity = employeeRepository.save(toSaveEntity);
        return modelMapper.map(saveEmployeeEntity, EmployeeDTO.class);
    }
    public void isExistsByEmployeeId(Long empId) {
        boolean exists = employeeRepository.existsById(empId);
        if (!exists) throw new ResourceNotFoundException("Employee with id " + empId + " does not exist");
    }
    public EmployeeDTO updateEmployeeById(EmployeeDTO employeeDTO, Long empId) {
        isExistsByEmployeeId(empId);
        EmployeeEntity employeeEntity = this.modelMapper.map(employeeDTO, EmployeeEntity.class);
        employeeEntity.setId(empId);
        EmployeeEntity saveEmployeeEntity = employeeRepository.save(employeeEntity);
        return this.modelMapper.map(saveEmployeeEntity, EmployeeDTO.class);
    }

    public boolean deleteEmployeeById(Long empId) {
        isExistsByEmployeeId(empId);
        this.employeeRepository.deleteById(empId);
        return true;
    }

    public EmployeeDTO updatePartialEmployeeById(Map<String, Object> updates, Long empId) {
        isExistsByEmployeeId(empId);
        EmployeeEntity employeeEntity = employeeRepository.findById(empId).orElse(null);
        updates.forEach((field, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findField(EmployeeEntity.class, field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
        });
        return this.modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
    }
}
