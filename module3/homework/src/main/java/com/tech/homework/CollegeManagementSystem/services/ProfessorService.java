package com.tech.homework.CollegeManagementSystem.services;

import com.tech.homework.CollegeManagementSystem.dto.*;
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
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ProfessorResponseDto> getAllProfessors(){

        List<Professor> professors = professorRepository.findAll();
        return professors
                .stream()
                .map(professor-> {
                    ProfessorResponseDto dto = new ProfessorResponseDto();
                    dto.setId(professor.getId());
                    dto.setTitle(professor.getTitle());

                    List<StudentSummaryDto>studentSummaryDtos = professor.getStudents()
                            .stream()
                            .map(student ->
                                    new StudentSummaryDto(student.getId(),student.getName())
                            ).toList();
                    dto.setStudents(studentSummaryDtos);

                    List<SubjectSummaryDto>subjectSummaryDtos = professor.getSubject()
                            .stream()
                            .map(subject ->
                                    new SubjectSummaryDto(subject.getId(),subject.getTitle())
                            ).toList();
                    dto.setStudents(studentSummaryDtos);
                    dto.setSubjects(subjectSummaryDtos);


                    return dto;
                })
                .collect(Collectors.toList());
    }

    public ProfessorResponseDto addProfessor(ProfessorRequestDto professorRequestDto){

        Professor professor = modelMapper.map(professorRequestDto,Professor.class);

//        Professor savedProfessor = professorRepository.save(professor);
        Professor savedProfessor = null;

        if(professorRequestDto.getStudentIds() != null && !professorRequestDto.getStudentIds().isEmpty()){
            List<Student> students = studentRepository.findAllById(professorRequestDto.getStudentIds());
            professor.setStudents(students);
            savedProfessor = professorRepository.save(professor);

            for (Student student : students){
                student.getProfessors().add(savedProfessor);  //add owning side
            }
            studentRepository.saveAll(students);
        }else{
            professorRepository.save(professor);
        }
        if(professorRequestDto.getSubjectIds() != null && !professorRequestDto.getSubjectIds().isEmpty()){
            List<Subject> subjects = subjectRepository.findAllById(professorRequestDto.getSubjectIds());
            professor.setSubject(subjects);
            savedProfessor = professorRepository.save(professor);
            for (Subject subject : subjects){
                subject.setProfessor(savedProfessor);
            }
            subjectRepository.saveAll(subjects);
        }else{
            professorRepository.save(professor);
        }

        return modelMapper.map(savedProfessor, ProfessorResponseDto.class);
    }

    public ProfessorResponseDto updateProfessorById(ProfessorRequestDto professorRequestDto, Long professorId){
        boolean isExist = professorRepository.existsById(professorId);
        if(!isExist) {
            throw new ResourceNotFoundException("Professor with " + professorId + "doesn't exists");
        }
        Professor professor = modelMapper.map(professorRequestDto,Professor.class);
        professor.setId(professorId);
        Professor updatedEntity = professorRepository.save(professor);
        return modelMapper.map(updatedEntity, ProfessorResponseDto.class);
    }

    public ProfessorResponseDto updatePartialProfessorById(Map<String,Object> updates, Long professorId){
        boolean isExist = professorRepository.existsById(professorId);
        if(!isExist) {
            throw new ResourceNotFoundException("Professor with " + professorId + "doesn't exists");
        }
        Professor professor = professorRepository.findById(professorId).orElse(null);
        updates.forEach((field,value)->{
            Field fieldToBeUpdated = ReflectionUtils.findField(Professor.class, field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, professor, value);
        });
        return this.modelMapper.map(professorRepository.save(Objects.requireNonNull(professor)), ProfessorResponseDto.class);
    }

    public Boolean deleteProfessor(Long professorId){
        boolean isExist = professorRepository.existsById(professorId);
        if(!isExist) throw new ResourceNotFoundException("Professor with "+professorId+"doesn't exists");
        professorRepository.deleteById(professorId);
        return true;
    }
}

