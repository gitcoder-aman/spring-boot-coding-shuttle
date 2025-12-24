package com.tech.homework.CollegeManagementSystem.controllers;

import com.tech.homework.CollegeManagementSystem.dto.AdmissionRecordDto;
import com.tech.homework.CollegeManagementSystem.services.AdmissionRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admissionRecord")
@RequiredArgsConstructor
public class AdmissionController {

    @Autowired
    private AdmissionRecordService admissionRecordService;

    @GetMapping
    public ResponseEntity<List<AdmissionRecordDto>> getAllProfessor() {
        List<AdmissionRecordDto> professorDtoList = admissionRecordService.getAllAdmissionRecord();
        return new ResponseEntity<>(professorDtoList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<AdmissionRecordDto> addAdmissionRecord(
            @Valid @RequestBody AdmissionRecordDto admissionRecordDto
    ) {
        AdmissionRecordDto saveAdmissionRecord = admissionRecordService.addAdmissionRecord(admissionRecordDto);
        return new ResponseEntity<>(saveAdmissionRecord, HttpStatus.CREATED);
    }

    @PutMapping("/{admissionRecordId}")
    public ResponseEntity<AdmissionRecordDto> updateAdmissionRecordById(
            @Valid @RequestBody AdmissionRecordDto admissionRecordDto,
            @PathVariable Long admissionRecordId
    ) {
        AdmissionRecordDto updatedAdmissionRecord = admissionRecordService.updateAdmissionRecordById(admissionRecordDto, admissionRecordId);
        return ResponseEntity.ok(updatedAdmissionRecord);
    }

    @PatchMapping("/{admissionRecordId}")
    public ResponseEntity<AdmissionRecordDto> updatePartialFieldAdmissionRecord(
            @RequestBody Map<String, Object> updates,
            @PathVariable Long admissionRecordId
    ) {
        AdmissionRecordDto updatedPartialAdmission = admissionRecordService.updatePartialAdmissionRecordById(updates,admissionRecordId);
        return ResponseEntity.ok(updatedPartialAdmission);
    }

    @DeleteMapping("/{professorId}")
    public ResponseEntity<Boolean>deleteStudentById(@PathVariable Long professorId){
        boolean isDeleted = admissionRecordService.deleteProfessor(professorId);

        if(isDeleted) return ResponseEntity.ok(true);
        else return ResponseEntity.notFound().build();
    }
}
