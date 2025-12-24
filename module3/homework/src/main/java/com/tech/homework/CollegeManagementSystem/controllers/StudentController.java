package com.tech.homework.CollegeManagementSystem.controllers;

import com.tech.homework.CollegeManagementSystem.dto.StudentDto;
import com.tech.homework.CollegeManagementSystem.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {


    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<StudentDto> studentDtoList = studentService.getAllStudents();
        return new ResponseEntity<>(studentDtoList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<StudentDto> addStudent(
            @Valid @RequestBody StudentDto studentDto
    ) {
        StudentDto saveStudent = studentService.addStudent(studentDto);
        return new ResponseEntity<>(saveStudent, HttpStatus.CREATED);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentDto> updateStudent(
            @Valid @RequestBody StudentDto studentDto,
            @PathVariable Long studentId
    ) {
        StudentDto updatedStudent = studentService.updateStudentById(studentDto, studentId);
        return ResponseEntity.ok(updatedStudent);
    }

    @PatchMapping("/{studentId}")
    public ResponseEntity<StudentDto> updatePartialFieldStudent(
            @RequestBody Map<String, Object> updates,
            @PathVariable Long studentId
    ) {
        StudentDto updatedPartialStudent = studentService.updatePartialStudentById(updates,studentId);
        return ResponseEntity.ok(updatedPartialStudent);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Boolean>deleteStudentById(@PathVariable Long studentId){
        boolean isDeleted = studentService.deleteStudent(studentId);

        if(isDeleted) return ResponseEntity.ok(true);
        else return ResponseEntity.notFound().build();
    }
}
