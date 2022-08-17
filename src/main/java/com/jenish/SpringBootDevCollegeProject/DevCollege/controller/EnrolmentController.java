package com.jenish.SpringBootDevCollegeProject.DevCollege.controller;

import com.jenish.SpringBootDevCollegeProject.DevCollege.Service.EnrolmentService;
import com.jenish.SpringBootDevCollegeProject.DevCollege.dto.EnrolmentModel;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Course;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Enrolment;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.EnrolmentNotFoundException;
import com.jenish.SpringBootDevCollegeProject.DevCollege.serviceImpl.EnrolmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class EnrolmentController {
    @Autowired
    EnrolmentServiceImpl enrolmentService;

    @PostMapping("/enrolment/addEnrolment")
    public String addEnrolment(@RequestBody @Valid EnrolmentModel enrolmentModel) {
        return enrolmentService.saveEnrolment(enrolmentModel);
    }

    @GetMapping("/enrolment/getEnrolment/{enrolmentId}")
    public Enrolment getEnrolmentById(@PathVariable String enrolmentId) throws EnrolmentNotFoundException {
        return enrolmentService.enrolmentById(enrolmentId);
    }

    @GetMapping("/enrolment/getAll")
    public List<Enrolment> getAllEnrolment() throws EnrolmentNotFoundException {
        return enrolmentService.getAllEnrolment();
    }
}
