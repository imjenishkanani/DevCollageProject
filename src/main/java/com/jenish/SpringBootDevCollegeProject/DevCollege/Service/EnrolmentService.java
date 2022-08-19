package com.jenish.SpringBootDevCollegeProject.DevCollege.Service;

import com.jenish.SpringBootDevCollegeProject.DevCollege.dto.EnrolmentModel;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Enrolment;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.CourseNotFoundException;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.EnrolmentNotFoundException;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.StudentNotFoundException;

import java.util.List;
import java.util.Map;

public interface EnrolmentService {
    public String saveEnrolment(EnrolmentModel enrolmentModel);
    public Enrolment enrolmentById(String enrolmentId) throws EnrolmentNotFoundException;

    public List<Enrolment> getAllEnrolment() throws EnrolmentNotFoundException;
    public List<Enrolment> enrolmentOfStudentById(String studentId) throws StudentNotFoundException;
    public String removeEnrolmentById(String enrolmentId) throws EnrolmentNotFoundException;
    public String updateStatus(String enrolmentId);
    public String checkCourseAvailability(String courseId) throws CourseNotFoundException;
    public Map<String, String> courseSuggest(String studentId) throws StudentNotFoundException;
}
