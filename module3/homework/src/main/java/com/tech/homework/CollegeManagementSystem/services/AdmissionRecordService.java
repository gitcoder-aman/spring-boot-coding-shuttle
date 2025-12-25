package com.tech.homework.CollegeManagementSystem.services;

import com.tech.homework.CollegeManagementSystem.dto.AdmissionRecordRequestDto;
import com.tech.homework.CollegeManagementSystem.dto.AdmissionRecordResponseDto;
import com.tech.homework.CollegeManagementSystem.entities.AdmissionRecord;
import com.tech.homework.CollegeManagementSystem.entities.Professor;
import com.tech.homework.CollegeManagementSystem.entities.Student;
import com.tech.homework.CollegeManagementSystem.exceptions.ResourceNotFoundException;
import com.tech.homework.CollegeManagementSystem.repositories.AdmissionRecordRepository;
import com.tech.homework.CollegeManagementSystem.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdmissionRecordService {

    private final AdmissionRecordRepository admissionRecordRepository;
    private final StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<AdmissionRecordResponseDto> getAllAdmissionRecord(){

        List<AdmissionRecord> admissionRecords = admissionRecordRepository.findAll();
        return admissionRecords
                .stream()
                .map(admissionRecord -> modelMapper.map(admissionRecord, AdmissionRecordResponseDto.class))
                .collect(Collectors.toList());
    }

    public AdmissionRecordResponseDto addAdmissionRecord(AdmissionRecordRequestDto admissionRecordRequestDto){

        AdmissionRecord admissionRecord = modelMapper.map(admissionRecordRequestDto,AdmissionRecord.class);

        if(admissionRecordRequestDto.getStudentId() != null){
            Student student = studentRepository.findById(admissionRecordRequestDto.getStudentId()).orElseThrow();
            admissionRecord.setStudent(student);
        }
        AdmissionRecord savedAdmissionRecord = admissionRecordRepository.save(admissionRecord);

        return modelMapper.map(savedAdmissionRecord, AdmissionRecordResponseDto.class);
    }

    public AdmissionRecordResponseDto updateAdmissionRecordById(AdmissionRecordRequestDto admissionRecordRequestDto, Long admissionRecordId){
        boolean isExist = admissionRecordRepository.existsById(admissionRecordId);
        if(!isExist) {
            throw new ResourceNotFoundException("Admission Record with " + admissionRecordId + "doesn't exists");
        }
        AdmissionRecord admissionRecord = modelMapper.map(admissionRecordRequestDto,AdmissionRecord.class);
        admissionRecord.setId(admissionRecordId);
        AdmissionRecord updatedEntity = admissionRecordRepository.save(admissionRecord);
        return modelMapper.map(updatedEntity, AdmissionRecordResponseDto.class);
    }

    public AdmissionRecordResponseDto updatePartialAdmissionRecordById(Map<String,Object> updates, Long admissionRecordId){
        boolean isExist = admissionRecordRepository.existsById(admissionRecordId);
        if(!isExist) {
            throw new ResourceNotFoundException("AdmissionRecord with " + admissionRecordId + "doesn't exists");
        }
        AdmissionRecord admissionRecord = admissionRecordRepository.findById(admissionRecordId).orElse(null);
        updates.forEach((field,value)->{
            Field fieldToBeUpdated = ReflectionUtils.findField(Professor.class, field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, admissionRecord, value);
        });
        return this.modelMapper.map(admissionRecordRepository.save(Objects.requireNonNull(admissionRecord)), AdmissionRecordResponseDto.class);
    }

    public Boolean deleteAdmissionRecord(Long admissionRecordId){
        boolean isExist = admissionRecordRepository.existsById(admissionRecordId);
        if(!isExist) throw new ResourceNotFoundException("AdmissionRecord with "+admissionRecordId+" doesn't exists");
        admissionRecordRepository.deleteById(admissionRecordId);
        return true;
    }
}

