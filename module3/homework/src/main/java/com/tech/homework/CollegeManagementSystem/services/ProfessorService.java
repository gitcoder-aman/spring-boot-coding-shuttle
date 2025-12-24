package com.tech.homework.CollegeManagementSystem.services;

import com.tech.homework.CollegeManagementSystem.dto.ProfessorDto;
import com.tech.homework.CollegeManagementSystem.dto.StudentDto;
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

    public List<ProfessorDto> getAllStudents(){

        List<Professor> professors = professorRepository.findAll();
        return professors
                .stream()
                .map(professor -> modelMapper.map(professor, ProfessorDto.class))
                .collect(Collectors.toList());
    }

    public ProfessorDto addProfessor(ProfessorDto professorDto){

        Professor professor = modelMapper.map(professorDto,Professor.class);
        Professor savedProfessor = professorRepository.save(professor);

        if(professorDto.getStudentIds() != null && !professorDto.getStudentIds().isEmpty()){
            List<Student> students = studentRepository.findAllById(professorDto.getStudentIds());

            for (Student student : students){
                student.getProfessors().add(savedProfessor);  //add owning side
            }
            studentRepository.saveAll(students);
        }
        if(professorDto.getSubjectIds() != null && !professorDto.getSubjectIds().isEmpty()){
            List<Subject> subjects = subjectRepository.findAllById(professorDto.getSubjectIds());

            for (Subject subject : subjects){
                subject.setProfessor(savedProfessor);
            }
            subjectRepository.saveAll(subjects);
        }
        return modelMapper.map(savedProfessor,ProfessorDto.class);
    }

    public ProfessorDto updateProfessorById(ProfessorDto professorDto,Long professorId){
        boolean isExist = professorRepository.existsById(professorId);
        if(!isExist) {
            throw new ResourceNotFoundException("Professor with " + professorId + "doesn't exists");
        }
        Professor professor = modelMapper.map(professorDto,Professor.class);
        professor.setId(professorId);
        Professor updatedEntity = professorRepository.save(professor);
        return modelMapper.map(updatedEntity, ProfessorDto.class);
    }

    public ProfessorDto updatePartialProfessorById(Map<String,Object> updates, Long professorId){
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
        return this.modelMapper.map(professorRepository.save(Objects.requireNonNull(professor)), ProfessorDto.class);
    }

    public Boolean deleteProfessor(Long professorId){
        boolean isExist = professorRepository.existsById(professorId);
        if(!isExist) throw new ResourceNotFoundException("Professor with "+professorId+"doesn't exists");
        professorRepository.deleteById(professorId);
        return true;
    }
}

