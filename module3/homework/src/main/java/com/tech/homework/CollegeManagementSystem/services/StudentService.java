package com.tech.homework.CollegeManagementSystem.services;

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
public class StudentService {

    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<StudentDto> getAllStudents(){

        List<Student> students = studentRepository.findAll();
        return students
                .stream()
                .map(student-> modelMapper.map(student, StudentDto.class))
                .collect(Collectors.toList());
    }

    public StudentDto addStudent(StudentDto studentDto){

        Student student = modelMapper.map(studentDto,Student.class);

        //  Set professors
        if (studentDto.getProfessorIds() != null && !studentDto.getProfessorIds().isEmpty()) {
            List<Professor> professors =
                    professorRepository.findAllById(studentDto.getProfessorIds());
            student.setProfessors(professors);
        }

        //  Set subjects
        if (studentDto.getSubjectIds() != null && !studentDto.getSubjectIds().isEmpty()) {
            List<Subject> subjects =
                    subjectRepository.findAllById(studentDto.getSubjectIds());
            student.setSubjects(subjects);
        }
        Student saveStudent = studentRepository.save(student);
        return modelMapper.map(saveStudent,StudentDto.class);
    }

    public StudentDto updateStudentById(StudentDto studentDto,Long studentId){
        boolean isExist = studentRepository.existsById(studentId);
        if(!isExist) {
            throw new ResourceNotFoundException("Student with " + studentId + "doesn't exists");
        }
        Student student = modelMapper.map(studentDto,Student.class);
        student.setId(studentId);
        Student updatedEntity = studentRepository.save(student);
        return modelMapper.map(updatedEntity, StudentDto.class);
    }

    public StudentDto updatePartialStudentById(Map<String,Object> updates, Long studentId){
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
        return this.modelMapper.map(studentRepository.save(Objects.requireNonNull(student)), StudentDto.class);
    }

    public Boolean deleteStudent(Long studentId){
        boolean isExist = studentRepository.existsById(studentId);
        if(!isExist) throw new ResourceNotFoundException("Student with "+studentId+"doesn't exists");
        studentRepository.deleteById(studentId);
        return true;
    }
}
