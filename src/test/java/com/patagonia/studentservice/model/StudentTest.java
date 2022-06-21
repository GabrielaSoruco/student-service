package com.patagonia.studentservice.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentTest {

    Student student;

    @BeforeEach
    void setUp() {
        student = new Student(1L, "Vainilla", "Micky", LocalDate.of(2000, 06, 21));
    }

    @Test
    void getId() {
        assertEquals(1L, student.getId());
    }

    @Test
    void getSurname() {
        assertEquals("Vainilla", student.getSurname());
    }

    @Test
    void getName() {
        assertEquals("Micky", student.getName());
    }

    @Test
    void getBirthday() {
        assertEquals(LocalDate.of(2000, 06, 21), student.getBirthday());
    }


    @Test
    void setId() {
        student.setId(3L);
        assertEquals(3L, student.getId());
    }


    @Test
    void setSurname() {
        student.setSurname("Suspenso");
        assertEquals("Suspenso", student.getSurname());
    }


    @Test
    void setName() {
        student.setName("Jorge");
        assertEquals("Jorge", student.getName());
    }

    @Test
    void setBirthday() {
        student.setBirthday(LocalDate.of(2007,10, 11));
        assertEquals(LocalDate.of(2007,10, 11), student.getBirthday());
    }
}