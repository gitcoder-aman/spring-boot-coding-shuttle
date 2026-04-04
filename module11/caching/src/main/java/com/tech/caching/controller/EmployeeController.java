package com.tech.caching.controller;

import com.tech.caching.advice.ApiResponse;
import com.tech.caching.dto.EmployeeDTO;
import com.tech.caching.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{empId}")
    public ResponseEntity<EmployeeDTO> getEmployeeId(@PathVariable(name = "empId") Long id) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeId(id);
        return ResponseEntity.ok(employeeDTO);
    }
    @GetMapping
    public List<EmployeeDTO> getAllEmployees(
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String sortBy
    ){
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeDTO>> createNewEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO saveEmployee = employeeService.createNewEmployee(employeeDTO);
//        return new ResponseEntity<ApiResponse<EmployeeDTO>>(saveEmployee, HttpStatus.CREATED);
        return new ResponseEntity<>(new ApiResponse<>(saveEmployee),HttpStatus.CREATED);
    }
    @PutMapping("/{empId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@Valid @RequestBody EmployeeDTO employeeDTO, @PathVariable Long empId){
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeDTO,empId));
    }

    @DeleteMapping("/{empId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long empId){
        boolean gotDeleted = employeeService.deleteEmployeeById(empId);
        if(gotDeleted) return ResponseEntity.ok(true);
        else return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{empId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String,Object> updates, @PathVariable Long empId){
        EmployeeDTO employeeDTO =  employeeService.updatePartialEmployeeById(updates,empId);
        if(employeeDTO == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }

//    @GetMapping("/{email}")
//    public ResponseEntity<EmployeeDTO>getEmployeeByEmail(@PathVariable String email){
//        EmployeeDTO employeeDTO = employeeService.getEmployeeByEmail(email);
//        if(employeeDTO == null)
//            return ResponseEntity.notFound().build();
//
//        return ResponseEntity.ok(employeeDTO);
//    }
}
