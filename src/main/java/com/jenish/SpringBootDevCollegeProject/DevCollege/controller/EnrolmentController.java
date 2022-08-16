package com.jenish.SpringBootDevCollegeProject.DevCollege.controller;

import com.jenish.SpringBootDevCollegeProject.DevCollege.dto.EnrolmentModel;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Enrolment;
import com.jenish.SpringBootDevCollegeProject.DevCollege.serviceImpl.EnrolmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EnrolmentController {
    @Autowired
    EnrolmentServiceImpl enrolmentService;

    @PostMapping("/enrolment/addEnrolment")
    public String addEnrolment(@RequestBody @Valid EnrolmentModel enrolmentModel) {
        return enrolmentService.saveEnrolment(enrolmentModel);
    }
}
