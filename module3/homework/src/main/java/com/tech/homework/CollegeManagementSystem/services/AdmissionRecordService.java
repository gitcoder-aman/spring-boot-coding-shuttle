package com.tech.homework.CollegeManagementSystem.services;

import com.tech.homework.CollegeManagementSystem.dto.AdmissionRecordDto;
import com.tech.homework.CollegeManagementSystem.entities.AdmissionRecord;
import com.tech.homework.CollegeManagementSystem.entities.Professor;
import com.tech.homework.CollegeManagementSystem.exceptions.ResourceNotFoundException;
import com.tech.homework.CollegeManagementSystem.repositories.AdmissionRecordRepository;
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

    @Autowired
    private ModelMapper modelMapper;

    public List<AdmissionRecordDto> getAllAdmissionRecord(){

        List<AdmissionRecord> admissionRecords = admissionRecordRepository.findAll();
        return admissionRecords
                .stream()
                .map(admissionRecord -> modelMapper.map(admissionRecord, AdmissionRecordDto.class))
                .collect(Collectors.toList());
    }

    public AdmissionRecordDto addAdmissionRecord(AdmissionRecordDto admissionRecordDto){

        AdmissionRecord toSaveEntity = modelMapper.map(admissionRecordDto,AdmissionRecord.class);
        AdmissionRecord admissionRecord = admissionRecordRepository.save(toSaveEntity);
        return modelMapper.map(admissionRecord,AdmissionRecordDto.class);
    }

    public AdmissionRecordDto updateAdmissionRecordById(AdmissionRecordDto admissionRecordDto,Long admissionRecordId){
        boolean isExist = admissionRecordRepository.existsById(admissionRecordId);
        if(!isExist) {
            throw new ResourceNotFoundException("Admission Record with " + admissionRecordId + "doesn't exists");
        }
        AdmissionRecord admissionRecord = modelMapper.map(admissionRecordDto,AdmissionRecord.class);
        admissionRecord.setId(admissionRecordId);
        AdmissionRecord updatedEntity = admissionRecordRepository.save(admissionRecord);
        return modelMapper.map(updatedEntity, AdmissionRecordDto.class);
    }

    public AdmissionRecordDto updatePartialAdmissionRecordById(Map<String,Object> updates, Long admissionRecordId){
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
        return this.modelMapper.map(admissionRecordRepository.save(Objects.requireNonNull(admissionRecord)), AdmissionRecordDto.class);
    }

    public Boolean deleteProfessor(Long admissionRecordId){
        boolean isExist = admissionRecordRepository.existsById(admissionRecordId);
        if(!isExist) throw new ResourceNotFoundException("AdmissionRecord with "+admissionRecordId+" doesn't exists");
        admissionRecordRepository.deleteById(admissionRecordId);
        return true;
    }
}

