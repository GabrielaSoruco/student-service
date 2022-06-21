package com.patagonia.studentservice.repository;

import com.patagonia.studentservice.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//@ActiveProfiles("test")
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository repository;

    @Test
    public void findAllStudents(){
        List<Student> students = repository.findAll();
        assertEquals(4, students.size());
    }
}
