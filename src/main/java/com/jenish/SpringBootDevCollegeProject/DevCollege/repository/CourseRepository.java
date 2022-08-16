package com.jenish.SpringBootDevCollegeProject.DevCollege.repository;

import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, String> {
}
