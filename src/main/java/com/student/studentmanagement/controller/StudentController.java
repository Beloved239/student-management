package com.student.studentmanagement.controller;

import com.student.studentmanagement.Entity.Student;
import com.student.studentmanagement.Exceptions.ResourceNotFoundException;
import com.student.studentmanagement.Repository.StudentRepository;
import com.student.studentmanagement.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;

    public StudentController(StudentService studentService) {
        super();
        this.studentService = studentService;
    }

//    get student by id rest api
//
    //get all students
    @GetMapping("/students")
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

//    @PostMapping("/students")
//    public Student createStudent(@RequestBody Student student){
//        return studentRepository.save(student);
//    }


    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id:" + id));
        return ResponseEntity.ok(student);
    }

@PostMapping("/students")
    public Student saveStudent(@RequestBody Student student){
        studentService.saveStudent(student);
        return ResponseEntity.ok(studentRepository.save(student)).getBody();
}
@GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model){
        model.addAttribute("student", studentService.getStudentById(id));
        return "edit_student";

    }
    
    @PostMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id:" + id));

//        Student existingStudent = studentService.getStudentById(id);

        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setEmail(studentDetails.getEmail());

        //save updated student object
        Student updatedStudent = studentRepository.save(student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity <Map<String, Boolean>> deleteStudent(@PathVariable Long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id:" + id));
        studentRepository.delete(student);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
