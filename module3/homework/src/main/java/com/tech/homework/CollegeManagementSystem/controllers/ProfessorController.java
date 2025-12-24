package com.tech.homework.CollegeManagementSystem.controllers;

import com.tech.homework.CollegeManagementSystem.dto.ProfessorDto;
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
    public ResponseEntity<List<ProfessorDto>> getAllProfessor() {
        List<ProfessorDto> professorDtoList = professorService.getAllStudents();
        return new ResponseEntity<>(professorDtoList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ProfessorDto> addStudent(
            @Valid @RequestBody ProfessorDto professorDto
    ) {
        ProfessorDto saveProfessor = professorService.addProfessor(professorDto);
        return new ResponseEntity<>(saveProfessor, HttpStatus.CREATED);
    }

    @PutMapping("/{professorId}")
    public ResponseEntity<ProfessorDto> updateStudent(
            @Valid @RequestBody ProfessorDto professorDto,
            @PathVariable Long professorId
    ) {
        ProfessorDto updatedProfessor = professorService.updateProfessorById(professorDto, professorId);
        return ResponseEntity.ok(updatedProfessor);
    }

    @PatchMapping("/{professorId}")
    public ResponseEntity<ProfessorDto> updatePartialFieldStudent(
            @RequestBody Map<String, Object> updates,
            @PathVariable Long professorId
    ) {
        ProfessorDto updatedPartialStudent = professorService.updatePartialProfessorById(updates,professorId);
        return ResponseEntity.ok(updatedPartialStudent);
    }

    @DeleteMapping("/{professorId}")
    public ResponseEntity<Boolean>deleteStudentById(@PathVariable Long professorId){
        boolean isDeleted = professorService.deleteProfessor(professorId);

        if(isDeleted) return ResponseEntity.ok(true);
        else return ResponseEntity.notFound().build();
    }
}
