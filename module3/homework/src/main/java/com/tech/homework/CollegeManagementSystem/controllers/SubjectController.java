package com.tech.homework.CollegeManagementSystem.controllers;

import com.tech.homework.CollegeManagementSystem.dto.SubjectDto;
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
    public ResponseEntity<List<SubjectDto>> getAllStudents() {
        List<SubjectDto> subbjectDtoList = subjectService.getAllSubjects();
        return new ResponseEntity<>(subbjectDtoList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<SubjectDto> addStudent(
            @Valid @RequestBody SubjectDto subjectDto
    ) {
        SubjectDto saveSubject = subjectService.addSubject(subjectDto);
        return new ResponseEntity<>(saveSubject, HttpStatus.CREATED);
    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<SubjectDto> updateStudent(
            @Valid @RequestBody SubjectDto subjectDto,
            @PathVariable Long subjectId
    ) {
        SubjectDto updatedStudent = subjectService.updateSubjectById(subjectDto, subjectId);
        return ResponseEntity.ok(updatedStudent);
    }

    @PatchMapping("/{subjectId}")
    public ResponseEntity<SubjectDto> updatePartialFieldStudent(
            @RequestBody Map<String, Object> updates,
            @PathVariable Long subjectId
    ) {
        SubjectDto updatedPartialStudent = subjectService.updatePartialSubjectById(updates,subjectId);
        return ResponseEntity.ok(updatedPartialStudent);
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<Boolean>deleteStudentById(@PathVariable Long subjectId){
        boolean isDeleted = subjectService.deleteSubject(subjectId);

        if(isDeleted) return ResponseEntity.ok(true);
        else return ResponseEntity.notFound().build();
    }
}
