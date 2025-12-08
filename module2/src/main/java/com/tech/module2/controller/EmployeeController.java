package com.tech.module2.controller;

import com.tech.module2.dto.EmployeeDTO;
import com.tech.module2.entities.EmployeeEntity;
import com.tech.module2.repository.EmployeeRepository;
import com.tech.module2.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "*")
public class EmployeeController {

//    @GetMapping(path = "/getSecretMessage")
//    public String getMySuperSecretMessage(){
//        return "Secret message: fsdfsdfds";
//    }a

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{empId}")
    public ResponseEntity<EmployeeDTO> getEmployeeId(@PathVariable(name = "empId") Long id) {
        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeId(id);
        return employeeDTO
                .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping
    public List<EmployeeDTO> getAllEmployees(
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String sortBy
    ){
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody EmployeeDTO employeeDTO){
        EmployeeDTO saveEmployee = employeeService.createNewEmployee(employeeDTO);
        return new ResponseEntity<>(saveEmployee, HttpStatus.CREATED);
    }
    @PutMapping("/{empId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody EmployeeDTO employeeDTO,@PathVariable Long empId){
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
}
