package com.patagonia.studentservice.repository;

import com.patagonia.studentservice.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository repository;

    @Test
    public void findAllStudents(){
        List<Student> students = repository.findAll();
        assertNotNull(students);
        assertEquals(4, students.size());
    }

    @Test
    void findById(){
        Optional<Student> student = repository.findById(2L);
        assertTrue(student.isPresent());
        assertEquals("Golang", student.get().getSurname());
    }

    @Test
    void saveStudent(){
        Student student = repository.save(new Student(5L, "Vainilla","Micky", LocalDate.of(1972,3,22)));
        assertEquals("Vainilla", student.getSurname());
    }

    @Test
    void updateStudent(){
        Student student = repository.findBySurname("Poroti");
        student.setSurname("Poroto");
        repository.save(student);
        assertEquals("Poroto", student.getSurname());
    }

    @Test
    void deleteStudent(){
        Student student = repository.save(new Student(5L, "Vainilla","Micky", LocalDate.of(1972,3,22)));
        repository.deleteById(student.getId());
        Student student1 = repository.findBySurname("Vainilla");
        assertNull(student1);
    }
}
