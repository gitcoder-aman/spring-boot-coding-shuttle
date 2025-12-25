package com.tech.homework.CollegeManagementSystem.controllers;

import com.tech.homework.CollegeManagementSystem.dto.SubjectRequestDto;
import com.tech.homework.CollegeManagementSystem.dto.SubjectResponseDto;
import com.tech.homework.CollegeManagementSystem.services.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {


    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<List<SubjectResponseDto>> getAllStudents() {
        List<SubjectResponseDto> subbjectDtoList = subjectService.getAllSubjects();
        return new ResponseEntity<>(subbjectDtoList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<SubjectResponseDto> addStudent(
            @Valid @RequestBody SubjectRequestDto subjectRequestDto
    ) {
        SubjectResponseDto saveSubject = subjectService.addSubject(subjectRequestDto);
        return new ResponseEntity<>(saveSubject, HttpStatus.CREATED);
    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<SubjectResponseDto> updateStudent(
            @Valid @RequestBody SubjectRequestDto subjectRequestDto,
            @PathVariable Long subjectId
    ) {
        SubjectResponseDto updatedStudent = subjectService.updateSubjectById(subjectRequestDto, subjectId);
        return ResponseEntity.ok(updatedStudent);
    }

    @PatchMapping("/{subjectId}")
    public ResponseEntity<SubjectResponseDto> updatePartialFieldStudent(
            @RequestBody Map<String, Object> updates,
            @PathVariable Long subjectId
    ) {
        SubjectResponseDto updatedPartialStudent = subjectService.updatePartialSubjectById(updates,subjectId);
        return ResponseEntity.ok(updatedPartialStudent);
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<Boolean>deleteStudentById(@PathVariable Long subjectId){
        boolean isDeleted = subjectService.deleteSubject(subjectId);

        if(isDeleted) return ResponseEntity.ok(true);
        else return ResponseEntity.notFound().build();
    }
}
