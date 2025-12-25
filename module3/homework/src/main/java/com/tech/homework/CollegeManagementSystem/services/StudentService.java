package com.tech.homework.CollegeManagementSystem.services;

import com.tech.homework.CollegeManagementSystem.dto.ProfessorSummaryDto;
import com.tech.homework.CollegeManagementSystem.dto.StudentRequestDto;
import com.tech.homework.CollegeManagementSystem.dto.StudentResponseDto;
import com.tech.homework.CollegeManagementSystem.dto.SubjectSummaryDto;
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
public class StudentService {

    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<StudentResponseDto> getAllStudents(){

        List<Student> students = studentRepository.findAll();
        return students
                .stream()
                .map(student-> {
                    StudentResponseDto dto = new StudentResponseDto();
                    dto.setId(student.getId());
                    dto.setName(student.getName());

                    List<ProfessorSummaryDto>professorSummaryDtos = student.getProfessors()
                            .stream()
                            .map(professor ->
                                new ProfessorSummaryDto(professor.getId(),professor.getTitle())
                            ).toList();

                    List<SubjectSummaryDto>subjectSummaryDtos = student.getSubjects()
                            .stream()
                            .map(subject ->
                                new SubjectSummaryDto(subject.getId(),subject.getTitle())
                            ).toList();
                    dto.setProfessors(professorSummaryDtos);
                    dto.setSubjects(subjectSummaryDtos);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public StudentResponseDto addStudent(StudentRequestDto studentRequestDto){

        Student student = modelMapper.map(studentRequestDto,Student.class);

        //  Set professors
        if (studentRequestDto.getProfessorIds() != null && !studentRequestDto.getProfessorIds().isEmpty()) {
            List<Professor> professors =
                    professorRepository.findAllById(studentRequestDto.getProfessorIds());
            student.setProfessors(professors);
        }

        //  Set subjects
        if (studentRequestDto.getSubjectIds() != null && !studentRequestDto.getSubjectIds().isEmpty()) {
            List<Subject> subjects =
                    subjectRepository.findAllById(studentRequestDto.getSubjectIds());
            student.setSubjects(subjects);
        }
        Student saveStudent = studentRepository.save(student);
        return modelMapper.map(saveStudent, StudentResponseDto.class);
    }

    public StudentResponseDto updateStudentById(StudentRequestDto studentRequestDto, Long studentId){
        boolean isExist = studentRepository.existsById(studentId);
        if(!isExist) {
            throw new ResourceNotFoundException("Student with " + studentId + "doesn't exists");
        }
        Student student = modelMapper.map(studentRequestDto,Student.class);
        student.setId(studentId);
        Student updatedEntity = studentRepository.save(student);
        return modelMapper.map(updatedEntity, StudentResponseDto.class);
    }

    public StudentResponseDto updatePartialStudentById(Map<String,Object> updates, Long studentId){
        boolean isExist = studentRepository.existsById(studentId);
        if(!isExist) {
            throw new ResourceNotFoundException("Student with " + studentId + "doesn't exists");
        }
        Student student = studentRepository.findById(studentId).orElse(null);
        updates.forEach((field,value)->{
            Field fieldToBeUpdated = ReflectionUtils.findField(Student.class, field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, student, value);
        });
        return this.modelMapper.map(studentRepository.save(Objects.requireNonNull(student)), StudentResponseDto.class);
    }

    public Boolean deleteStudent(Long studentId){
        boolean isExist = studentRepository.existsById(studentId);
        if(!isExist) throw new ResourceNotFoundException("Student with "+studentId+"doesn't exists");
        studentRepository.deleteById(studentId);
        return true;
    }
}
