package com.patagonia.studentservice.service.impl;

import com.patagonia.studentservice.exceptions.StudentNotFoundException;
import com.patagonia.studentservice.model.Student;
import com.patagonia.studentservice.repository.StudentRepository;
import com.patagonia.studentservice.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
@Service
public class StudentServiceImpl implements IStudentService {
    @Autowired
    private StudentRepository repository;
    @Override
    public List<Student> findAll() {
        return repository.findAll();
    }
    @Override
    public Student findById(Long id) {
        return repository.findById(id)
                .orElseThrow(()-> new StudentNotFoundException(id));
    }
    @Transactional
    @Override
    public Student createStudent(Student student) {
        return repository.save(student);
    }
    @Transactional
    @Override
    public void updateStudent(Long id, Student student) {
        Student studentToUpdate = this.findById(id);
        studentToUpdate.setName(student.getName());
        studentToUpdate.setSurname(studentToUpdate.getSurname());
        studentToUpdate.setBirthday(student.getBirthday());
        repository.save(studentToUpdate);
    }
    @Transactional
    @Override
    public String deleteStudent(Long id) {
        if (!repository.existsById(id)){
            throw new StudentNotFoundException(id);
        }
        repository.deleteById(id);
        return "Student successfully deleted";
    }

    // calcular la edad promedio de los students
    public Integer ageAverage(){
        List<Student> students = repository.findAll();
        return (int) students.stream().mapToInt(s-> Period.between(s.getBirthday(), LocalDate.now()).getYears())
                .average()
                .orElse(0);
    }

    // Mostrar el student con más edad y con menos edad.
    Comparator<Student> comparatorYear = Comparator.comparingInt(student -> Period.between(student.getBirthday(), LocalDate.now()).getYears());
    public Student olderStudent(){
        List<Student> students = repository.findAll();
        return students.stream()
                .max(comparatorYear)
                .orElseThrow(NoSuchElementException::new);
    }
    public Student youngerStudent(){
        List<Student> students = repository.findAll();
        return students.stream()
                .min(comparatorYear)
                .orElseThrow(()-> new NoSuchElementException("No hay elementos que respondan a la condición"));
    }

    // Mostrar solo el id, surname y name de los students. Por ejemplo: 1 - , Lovelace, Ada
    public String getFullNameStudents(){
        List<Student> students = repository.findAll();
        return students.stream().map(s->s.getId() + " - ,"+ s.getSurname() + ", " + s.getName())
                .collect(Collectors.joining(" "));
    }

    // Listar students mayores de edad y los menores de edad.
    public List<Student> getAdultsStudents(){
        List<Student> students = repository.findAll();
        return students.stream()
                .filter(s->Period.between(s.getBirthday(), LocalDate.now()).getYears() >= 18)
                .collect(Collectors.toList());
    }

    public List<Student> getMinorsStudents(){
        List<Student> students = repository.findAll();
        return students.stream()
                .filter(s->Period.between(s.getBirthday(), LocalDate.now()).getYears() < 18)
                .collect(Collectors.toList());
    }

    // Mostrar la edad promedio de los mayores de edad.
    public Integer adultsAverage(){
        List<Student> students = repository.findAll();
        return (int) students.stream()
                .mapToInt(student -> Period.between(student.getBirthday(), LocalDate.now()).getYears())
                .filter(year -> year >= 18)
                .average()
                .orElse(0);
    }

    // Mostrar la edad promedio de los menores de edad.
    public Integer minorsAverage(){
        List<Student> students = repository.findAll();
        return (int) students.stream()
                .mapToInt(student -> Period.between(student.getBirthday(), LocalDate.now()).getYears())
                .filter(years -> years < 18)
                .average()
                .orElse(0);
    }
}
