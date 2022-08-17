package com.jenish.SpringBootDevCollegeProject.DevCollege.Service;

import com.jenish.SpringBootDevCollegeProject.DevCollege.dto.EnrolmentModel;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Enrolment;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.EnrolmentNotFoundException;

import java.util.List;

public interface EnrolmentService {
    public String saveEnrolment(EnrolmentModel enrolmentModel);
    public Enrolment enrolmentById(String enrolmentId) throws EnrolmentNotFoundException;

    public List<Enrolment> getAllEnrolment() throws EnrolmentNotFoundException;
}
