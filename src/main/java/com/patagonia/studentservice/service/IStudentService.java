package com.patagonia.studentservice.service;

import com.patagonia.studentservice.model.Student;

import java.util.List;

public interface IStudentService {

    List<Student> findAll();

    Student findById(Long id);

    Student createStudent(Student student);

    void updateStudent(Long id, Student student);

    String deleteStudent(Long id);

    Integer ageAverage();

    Student olderStudent();

    Student youngerStudent();

    String getFullNameStudents();

    List<Student> getAdultsStudents();

    List<Student> getMinorsStudents();

    Integer adultsAverage();

    Integer minorsAverage();
}
