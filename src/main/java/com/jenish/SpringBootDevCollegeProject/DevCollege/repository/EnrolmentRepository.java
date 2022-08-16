package com.jenish.SpringBootDevCollegeProject.DevCollege.repository;

import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Enrolment;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrolmentRepository extends JpaRepository<Enrolment, String> {

}
