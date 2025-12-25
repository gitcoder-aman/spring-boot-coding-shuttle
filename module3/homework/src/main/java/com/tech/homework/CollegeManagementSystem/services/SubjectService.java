package com.tech.homework.CollegeManagementSystem.services;

import com.tech.homework.CollegeManagementSystem.dto.SubjectRequestDto;
import com.tech.homework.CollegeManagementSystem.dto.SubjectResponseDto;
import com.tech.homework.CollegeManagementSystem.entities.Professor;
import com.tech.homework.CollegeManagementSystem.entities.Student;
import com.tech.homework.CollegeManagementSystem.entities.Subject;
import com.tech.homework.CollegeManagementSystem.exceptions.ResourceNotFoundException;
import com.tech.homework.CollegeManagementSystem.repositories.ProfessorRepository;
import com.tech.homework.CollegeManagementSystem.repositories.StudentRepository;
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
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<SubjectResponseDto> getAllSubjects(){

        List<Subject> subjects = subjectRepository.findAll();
        return subjects
                .stream()
                .map(subject-> modelMapper.map(subject, SubjectResponseDto.class))
                .collect(Collectors.toList());
    }

    public SubjectResponseDto addSubject(SubjectRequestDto subjectRequestDto){

        Subject subject = modelMapper.map(subjectRequestDto,Subject.class);

        if(subjectRequestDto.getProfessorId() != null){
            Professor professor = professorRepository.findById(subjectRequestDto.getProfessorId()).orElseThrow();
            subject.setProfessor(professor);
        }

        Subject savedSubject = subjectRepository.save(subject);


        if(subjectRequestDto.getStudentIds() != null && !subjectRequestDto.getStudentIds().isEmpty()){

            List<Student>students = studentRepository.findAllById(subjectRequestDto.getStudentIds());
            for (Student student : students){
                student.getSubjects().add(savedSubject);  // add owning side
            }
            studentRepository.saveAll(students);
        }

        return modelMapper.map(subject, SubjectResponseDto.class);
    }

    public SubjectResponseDto updateSubjectById(SubjectRequestDto subjectRequestDto, Long subjectId){
        boolean isExist = subjectRepository.existsById(subjectId);
        if(!isExist) {
            throw new ResourceNotFoundException("Subject with " + subjectId + "doesn't exists");
        }
        Subject subject = modelMapper.map(subjectRequestDto,Subject.class);
        subject.setId(subjectId);
        Subject updatedEntity = subjectRepository.save(subject);
        return modelMapper.map(updatedEntity, SubjectResponseDto.class);
    }

    public SubjectResponseDto updatePartialSubjectById(Map<String,Object> updates, Long subjectId){
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
        return this.modelMapper.map(subjectRepository.save(Objects.requireNonNull(subject)), SubjectResponseDto.class);
    }

    public Boolean deleteSubject(Long subjectId){
        boolean isExist = subjectRepository.existsById(subjectId);
        if(!isExist) throw new ResourceNotFoundException("Subject with "+subjectId+"doesn't exists");
        subjectRepository.deleteById(subjectId);
        return true;
    }
}
