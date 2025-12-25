package com.tech.homework.CollegeManagementSystem.controllers;

import com.tech.homework.CollegeManagementSystem.dto.AdmissionRecordRequestDto;
import com.tech.homework.CollegeManagementSystem.dto.AdmissionRecordResponseDto;
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
    public ResponseEntity<List<AdmissionRecordResponseDto>> getAllProfessor() {
        List<AdmissionRecordResponseDto> professorDtoList = admissionRecordService.getAllAdmissionRecord();
        return new ResponseEntity<>(professorDtoList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<AdmissionRecordResponseDto> addAdmissionRecord(
            @Valid @RequestBody AdmissionRecordRequestDto admissionRecordRequestDto
    ) {
        AdmissionRecordResponseDto saveAdmissionRecord = admissionRecordService.addAdmissionRecord(admissionRecordRequestDto);
        return new ResponseEntity<>(saveAdmissionRecord, HttpStatus.CREATED);
    }

    @PutMapping("/{admissionRecordId}")
    public ResponseEntity<AdmissionRecordResponseDto> updateAdmissionRecordById(
            @Valid @RequestBody AdmissionRecordRequestDto admissionRecordRequestDto,
            @PathVariable Long admissionRecordId
    ) {
        AdmissionRecordResponseDto updatedAdmissionRecord = admissionRecordService.updateAdmissionRecordById(admissionRecordRequestDto, admissionRecordId);
        return ResponseEntity.ok(updatedAdmissionRecord);
    }

    @PatchMapping("/{admissionRecordId}")
    public ResponseEntity<AdmissionRecordResponseDto> updatePartialFieldAdmissionRecord(
            @RequestBody Map<String, Object> updates,
            @PathVariable Long admissionRecordId
    ) {
        AdmissionRecordResponseDto updatedPartialAdmission = admissionRecordService.updatePartialAdmissionRecordById(updates,admissionRecordId);
        return ResponseEntity.ok(updatedPartialAdmission);
    }

    @DeleteMapping("/{admissionRecordId}")
    public ResponseEntity<Boolean>deleteStudentById(@PathVariable Long professorId){
        boolean isDeleted = admissionRecordService.deleteAdmissionRecord(professorId);

        if(isDeleted) return ResponseEntity.ok(true);
        else return ResponseEntity.notFound().build();
    }
}
