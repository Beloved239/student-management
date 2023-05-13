package com.student.studentmanagement.Repository;


import com.student.studentmanagement.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
