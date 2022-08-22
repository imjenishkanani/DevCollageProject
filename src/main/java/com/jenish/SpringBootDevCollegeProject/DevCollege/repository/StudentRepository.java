package com.jenish.SpringBootDevCollegeProject.DevCollege.repository;

import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, String> {
    public Student findByStudentId(@Param("id") String id);
}
