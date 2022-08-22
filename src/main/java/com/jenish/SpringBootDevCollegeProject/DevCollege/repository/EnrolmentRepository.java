package com.jenish.SpringBootDevCollegeProject.DevCollege.repository;

import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Enrolment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrolmentRepository extends JpaRepository<Enrolment, String> {
    @Query(nativeQuery=true, value="Select * from enrolment")
    public List<Enrolment> getAllEnrolments();
    Enrolment findByEnrolmentId(@Param("id") String id);
}
