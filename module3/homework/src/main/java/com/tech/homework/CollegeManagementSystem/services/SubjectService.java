package com.tech.homework.CollegeManagementSystem.services;

import com.tech.homework.CollegeManagementSystem.dto.StudentDto;
import com.tech.homework.CollegeManagementSystem.dto.SubjectDto;
import com.tech.homework.CollegeManagementSystem.entities.Student;
import com.tech.homework.CollegeManagementSystem.entities.Subject;
import com.tech.homework.CollegeManagementSystem.exceptions.ResourceNotFoundException;
import com.tech.homework.CollegeManagementSystem.repositories.SubjectRepository;
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
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<SubjectDto> getAllSubjects(){

        List<Subject> subjects = subjectRepository.findAll();
        return subjects
                .stream()
                .map(subject-> modelMapper.map(subject, SubjectDto.class))
                .collect(Collectors.toList());
    }

    public SubjectDto addSubject(SubjectDto subjectDto){

        Subject toSaveEntity = modelMapper.map(subjectDto,Subject.class);
        Subject subject = subjectRepository.save(toSaveEntity);
        return modelMapper.map(subject,SubjectDto.class);
    }

    public SubjectDto updateSubjectById(SubjectDto subjectDto,Long subjectId){
        boolean isExist = subjectRepository.existsById(subjectId);
        if(!isExist) {
            throw new ResourceNotFoundException("Subject with " + subjectId + "doesn't exists");
        }
        Subject subject = modelMapper.map(subjectDto,Subject.class);
        subject.setId(subjectId);
        Subject updatedEntity = subjectRepository.save(subject);
        return modelMapper.map(updatedEntity, SubjectDto.class);
    }

    public SubjectDto updatePartialSubjectById(Map<String,Object> updates, Long subjectId){
        boolean isExist = subjectRepository.existsById(subjectId);
        if(!isExist) {
            throw new ResourceNotFoundException("Subject with " + subjectId + "doesn't exists");
        }
        Subject subject = subjectRepository.findById(subjectId).orElse(null);
        updates.forEach((field,value)->{
            Field fieldToBeUpdated = ReflectionUtils.findField(Student.class, field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, subject, value);
        });
        return this.modelMapper.map(subjectRepository.save(Objects.requireNonNull(subject)), SubjectDto.class);
    }

    public Boolean deleteSubject(Long subjectId){
        boolean isExist = subjectRepository.existsById(subjectId);
        if(!isExist) throw new ResourceNotFoundException("Subject with "+subjectId+"doesn't exists");
        subjectRepository.deleteById(subjectId);
        return true;
    }
}
