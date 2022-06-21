package com.patagonia.studentservice.service.impl;

import com.patagonia.studentservice.model.Student;
import com.patagonia.studentservice.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = StudentServiceImplTest.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private final List<Student> studentsList = new ArrayList<>();
    private Student student;

    @BeforeEach
    void setUp(){
        student = new Student(1L, "Suspenso", "Jorge", LocalDate.of(2002,1,10));
        studentsList.add(student);
        studentsList.add(new Student(2L, "Vainilla", "Micky", LocalDate.of(1966,6,6)));
        studentsList.add(new Student(3L, "Saborido", "Pedro", LocalDate.of(1962,4,24)));
        studentsList.add(new Student(4L, "Rivas", "Violencia", LocalDate.of(2018,5,25)));
    }

    @Test
    void findAll() {
        when(studentRepository.findAll()).thenReturn(studentsList);
        assertNotNull(studentService.findAll());
    }

    @Test
    void findById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        assertEquals("Suspenso", studentService.findById(1L).getSurname());
    }

    @Test
    void createStudent() {
        when(studentRepository.save(student)).thenReturn(student);
        Student studentSaved = studentService.createStudent(student);
        assertEquals("Jorge", studentSaved.getName());
    }

    @Test
    void updateStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Student newStudent = new Student(1L, "Solanas", "Latino", LocalDate.of(1970,2,22));
        studentService.updateStudent(newStudent.getId(), newStudent);
        assertEquals("Latino", student.getName());
    }

    @Test
    void deleteStudent() {
        doNothing().when(studentRepository).deleteById(student.getId());
        when(studentRepository.existsById(1L)).thenReturn(true);
        studentService.deleteStudent(1L);
        verify(studentRepository).deleteById(student.getId());
    }

    @Test
    void ageAverage() {
        when(studentRepository.findAll()).thenReturn(studentsList);
        assertEquals((int) studentsList.stream().mapToInt(s-> Period.between(s.getBirthday(), LocalDate.now()).getYears()).average().orElse(0), studentService.ageAverage());
    }

    @Test
    void olderStudent() {
        when(studentRepository.findAll()).thenReturn(studentsList);
        assertEquals(studentsList.get(2), studentService.olderStudent());
    }

    @Test
    void youngerStudent() {
        when(studentRepository.findAll()).thenReturn(studentsList);
        assertEquals(studentsList.get(3), studentService.youngerStudent());
    }

    @Test
    void getFullNameStudents() {
        when(studentRepository.findAll()).thenReturn(studentsList);
        assertNotNull(studentService.getFullNameStudents());
    }

    @Test
    void getAdultsStudents() {
        Student student1 = new Student(1L, "Suspenso", "Jorge", LocalDate.of(2002,1,10));
        Student student2 = new Student(2L, "Vainilla", "Micky", LocalDate.of(1966,6,6));
        Student student3 = new Student(3L, "Saborido", "Pedro", LocalDate.of(1962,4,24));
        when(studentRepository.findAll()).thenReturn(studentsList);
        assertEquals(Arrays.asList(student1, student2, student3), studentService.getAdultsStudents());
    }

    @Test
    void getMinorsStudents() {
        when(studentRepository.findAll()).thenReturn(studentsList);
        List<Student> minors = List.of(new Student(4L, "Rivas", "Violencia", LocalDate.of(2018, 5, 25)));
        assertEquals(minors, studentService.getMinorsStudents());
    }

    @Test
    void adultsAverage() {
        when(studentRepository.findAll()).thenReturn(studentsList);
        // 20 + 56 + 60
        assertEquals(45, studentService.adultsAverage());
    }

    @Test
    void minorsAverage() {
        when(studentRepository.findAll()).thenReturn(studentsList);
        // 4
        assertEquals(4, studentService.minorsAverage());
    }
}