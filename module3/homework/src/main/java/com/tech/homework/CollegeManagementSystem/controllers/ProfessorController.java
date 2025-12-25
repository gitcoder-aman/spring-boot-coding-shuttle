package com.tech.homework.CollegeManagementSystem.controllers;

import com.tech.homework.CollegeManagementSystem.dto.ProfessorRequestDto;
import com.tech.homework.CollegeManagementSystem.dto.ProfessorResponseDto;
import com.tech.homework.CollegeManagementSystem.services.ProfessorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/professors")
@RequiredArgsConstructor
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public ResponseEntity<List<ProfessorResponseDto>> getAllProfessor() {
        List<ProfessorResponseDto> professorRequestDtoList = professorService.getAllProfessors();
        return new ResponseEntity<>(professorRequestDtoList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ProfessorResponseDto> addStudent(
            @Valid @RequestBody ProfessorRequestDto professorRequestDto
    ) {
        ProfessorResponseDto saveProfessor = professorService.addProfessor(professorRequestDto);
        return new ResponseEntity<>(saveProfessor, HttpStatus.CREATED);
    }

    @PutMapping("/{professorId}")
    public ResponseEntity<ProfessorResponseDto> updateStudent(
            @Valid @RequestBody ProfessorRequestDto professorRequestDto,
            @PathVariable Long professorId
    ) {
        ProfessorResponseDto updatedProfessor = professorService.updateProfessorById(professorRequestDto, professorId);
        return ResponseEntity.ok(updatedProfessor);
    }

    @PatchMapping("/{professorId}")
    public ResponseEntity<ProfessorResponseDto> updatePartialFieldStudent(
            @RequestBody Map<String, Object> updates,
            @PathVariable Long professorId
    ) {
        ProfessorResponseDto updatedPartialStudent = professorService.updatePartialProfessorById(updates,professorId);
        return ResponseEntity.ok(updatedPartialStudent);
    }

    @DeleteMapping("/{professorId}")
    public ResponseEntity<Boolean>deleteStudentById(@PathVariable Long professorId){
        boolean isDeleted = professorService.deleteProfessor(professorId);

        if(isDeleted) return ResponseEntity.ok(true);
        else return ResponseEntity.notFound().build();
    }
}
