package com.patagonia.studentservice.repository;

import com.patagonia.studentservice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findBySurname(String surname);
}
