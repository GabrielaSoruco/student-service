package com.patagonia.studentservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.patagonia.studentservice.model.Student;
import com.patagonia.studentservice.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentServiceImpl studentService;

    ObjectMapper objectMapper;
    Student student;
    List<Student> studentsList = new ArrayList<>();

    @BeforeEach
    void setUp(){
        student = new Student(1L, "Suspenso", "Jorge", LocalDate.of(2002,1,10));
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void getAllStudents() throws Exception {
        studentsList.add(student);
        studentsList.add(new Student(2L, "Vainilla", "Micky", LocalDate.of(1966,6,6)));
        studentsList.add(new Student(3L, "Saborido", "Pedro", LocalDate.of(1962,4,24)));
        studentsList.add(new Student(4L, "Rivas", "Violencia", LocalDate.of(2018,5,25)));
        when(studentService.findAll()).thenReturn(studentsList);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/students").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(studentService).findAll();
    }

    @Test
    void getStudentById() throws Exception {
        when(studentService.findById(student.getId())).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/students/{id}",1L).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.surname").value("Suspenso"));
    }

    @Test
    void addStudent() throws Exception {
        when(studentService.createStudent(student)).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/students")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(student)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.name").value("Jorge"));
    }

    @Test
    void updateStudent() throws Exception {
        doNothing().when(studentService).updateStudent(student.getId(), student);
        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/students/update/{id}", student.getId())
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(student)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(studentService).updateStudent(student.getId(), student);
    }

    @Test
    void deleteStudent() throws Exception {
        doNothing().when(studentService).deleteStudent(student.getId());
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/students/delete/{id}",1L))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(studentService).deleteStudent(student.getId());
    }

    @Test
    void getAgeAverage() throws Exception {
        when(studentService.ageAverage()).thenReturn(35);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/students/age/average")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string("35"));
        verify(studentService).ageAverage();
    }

    @Test
    void getOlderStudent() throws Exception {
        Student older = new Student(3L, "Saborido", "Pedro", LocalDate.of(1962,4,24));
        when(studentService.olderStudent()).thenReturn(older);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/students/adults/older")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.name").value("Pedro"))
                .andDo(print());
        verify(studentService).olderStudent();
    }

    @Test
    void getYoungerStudent() throws Exception {
        Student younger = new Student(4L, "Rivas", "Violencia", LocalDate.of(2018,5,25));
        when(studentService.youngerStudent()).thenReturn(younger);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/students/minors/younger")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.name").value("Violencia"))
                .andDo(print());
        verify(studentService).youngerStudent();
    }

    @Test
    void getFullNameStudents() throws Exception {
        String fullnames = "1 - , Suspenso, Jorge 2 - , Vainilla, Micky 3 - , Saborido, Pedro 4 - , Rivas, Violencia";
        when(studentService.getFullNameStudents()).thenReturn(fullnames);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/students/fullname").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
        verify(studentService).getFullNameStudents();
    }

    @Test
    void getAdultsStudents() throws Exception {
        Student student1 = new Student(1L, "Suspenso", "Jorge", LocalDate.of(2002,1,10));
        Student student2 = new Student(2L, "Vainilla", "Micky", LocalDate.of(1966,6,6));
        Student student3 = new Student(3L, "Saborido", "Pedro", LocalDate.of(1962,4,24));
        when(studentService.getAdultsStudents()).thenReturn(Arrays.asList(student1, student2, student3));
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/students/adults").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].name").value("Jorge"))
                .andDo(print());
    }

    @Test
    void getMinorsStudents() throws Exception {
        Student student1 = new Student(4L, "Rivas", "Violencia", LocalDate.of(2018,5,25));
        Student student2 = new Student(5L, "Vainilla", "Micky", LocalDate.of(2021,6,6));
        Student student3 = new Student(6L, "Solanas", "Latino", LocalDate.of(2020,7,6));
        when(studentService.getMinorsStudents()).thenReturn(List.of(student1, student2));
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/students/minors").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].name").value("Violencia"))
                .andDo(print());
    }

    @Test
    void getAdultsAverage() throws Exception {
        when(studentService.adultsAverage()).thenReturn(45);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/students/age/average/adults")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getMinorsAverage() throws Exception {
        when(studentService.minorsAverage()).thenReturn(4);
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/students/age/average/minors").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string("4"));
    }
}