package com.jenish.SpringBootDevCollegeProject.DevCollege.repository;

import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Course;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}
