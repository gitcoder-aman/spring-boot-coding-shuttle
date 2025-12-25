package com.tech.homework.CollegeManagementSystem.controllers;

import com.tech.homework.CollegeManagementSystem.dto.StudentRequestDto;
import com.tech.homework.CollegeManagementSystem.dto.StudentResponseDto;
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
    public ResponseEntity<List<StudentResponseDto>> getAllStudents() {
        List<StudentResponseDto> studentRequestDtoList = studentService.getAllStudents();
        return new ResponseEntity<>(studentRequestDtoList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<StudentResponseDto> addStudent(
            @Valid @RequestBody StudentRequestDto studentRequestDto
    ) {
        StudentResponseDto saveStudent = studentService.addStudent(studentRequestDto);
        return new ResponseEntity<>(saveStudent, HttpStatus.CREATED);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentResponseDto> updateStudent(
            @Valid @RequestBody StudentRequestDto studentRequestDto,
            @PathVariable Long studentId
    ) {
        StudentResponseDto updatedStudent = studentService.updateStudentById(studentRequestDto, studentId);
        return ResponseEntity.ok(updatedStudent);
    }

    @PatchMapping("/{studentId}")
    public ResponseEntity<StudentResponseDto> updatePartialFieldStudent(
            @RequestBody Map<String, Object> updates,
            @PathVariable Long studentId
    ) {
        StudentResponseDto updatedPartialStudent = studentService.updatePartialStudentById(updates,studentId);
        return ResponseEntity.ok(updatedPartialStudent);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Boolean>deleteStudentById(@PathVariable Long studentId){
        boolean isDeleted = studentService.deleteStudent(studentId);

        if(isDeleted) return ResponseEntity.ok(true);
        else return ResponseEntity.notFound().build();
    }
}
