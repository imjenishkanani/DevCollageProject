package com.jenish.SpringBootDevCollegeProject.DevCollege.controller;

import com.jenish.SpringBootDevCollegeProject.DevCollege.Service.EnrolmentService;
import com.jenish.SpringBootDevCollegeProject.DevCollege.dto.EnrolmentModel;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Enrolment;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.CourseNotFoundException;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.EnrolmentNotFoundException;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class EnrolmentController {
    @Autowired
    EnrolmentService enrolmentService;

    @PostMapping("/enrolment/addEnrolment")
    public String addEnrolment(@RequestBody @Valid EnrolmentModel enrolmentModel) throws CourseNotFoundException, StudentNotFoundException {
        return enrolmentService.saveEnrolment(enrolmentModel);
    }

    @GetMapping("/enrolment/getEnrolment/{enrolmentId}")
    public Enrolment getEnrolmentById(@PathVariable String enrolmentId) throws EnrolmentNotFoundException {
        return enrolmentService.enrolmentById(enrolmentId);
    }

    @GetMapping("/enrolment/getEnrolDetailOfStudent/{studentId}")
    public List<Enrolment> getEnrolmentDetailOfStudent(@PathVariable String studentId) throws StudentNotFoundException {
        return enrolmentService.enrolmentOfStudentById(studentId);
    }

    @GetMapping("/enrolment/getAll")
    public List<Enrolment> getAllEnrolment() throws EnrolmentNotFoundException {
        return enrolmentService.getAllEnrolment();
    }

    @DeleteMapping("/enrolment/deleteEnrolment/{enrolmentId}")
    public String deleteEnrolmentById(@PathVariable String enrolmentId) throws EnrolmentNotFoundException {
        return enrolmentService.removeEnrolmentById(enrolmentId);
    }

    @PostMapping("/enrolment/status/{enrolmentId}")
    public String changeStatus(@PathVariable String enrolmentId) throws EnrolmentNotFoundException {
        return enrolmentService.updateStatus(enrolmentId);
    }

    @GetMapping("/courseAvailability/{courseId}")
    public String courseAvailability(@PathVariable String courseId) throws CourseNotFoundException {
        return enrolmentService.checkCourseAvailability(courseId);
    }

    @GetMapping("/Suggestion/{studentId}")
    public Map<String, String> courseSuggestion(@PathVariable String studentId) throws StudentNotFoundException{
        return enrolmentService.courseSuggest(studentId);
    }

}
