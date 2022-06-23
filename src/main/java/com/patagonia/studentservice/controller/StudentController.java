package com.patagonia.studentservice.controller;

import com.patagonia.studentservice.model.Student;
import com.patagonia.studentservice.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private IStudentService service;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id){
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        return new ResponseEntity<>(service.createStudent(student), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student student){
        service.updateStudent(id, student);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){
        return ResponseEntity.ok(service.deleteStudent(id));
    }

    @GetMapping("/ageAverage")
    public ResponseEntity<Integer> getAgeAverage(){
        return ResponseEntity.ok(service.ageAverage());
    }

    @GetMapping("/older")
    public ResponseEntity<Student> getOlderStudent(){
        return ResponseEntity.ok(service.olderStudent());
    }

    @GetMapping("/younger")
    public ResponseEntity<Student> getYoungerStudent(){
        return ResponseEntity.ok(service.youngerStudent());
    }

    @GetMapping("/fullname")
    public ResponseEntity<String> getFullNameStudents(){
        return ResponseEntity.ok(service.getFullNameStudents());
    }

    @GetMapping("/adults")
    public ResponseEntity<List<Student>> getAdultsStudents(){
        return ResponseEntity.ok(service.getAdultsStudents());
    }

    @GetMapping("/minors")
    public ResponseEntity<List<Student>> getMinorsStudents(){
        return ResponseEntity.ok(service.getMinorsStudents());
    }

    @GetMapping("/ageAverageAdults")
    public ResponseEntity<Integer> getAdultsAverage(){
        return ResponseEntity.ok(service.adultsAverage());
    }

    @GetMapping("/ageAverageMinors")
    public ResponseEntity<Integer> getMinorsAverage(){
        return ResponseEntity.ok(service.minorsAverage());
    }
}
