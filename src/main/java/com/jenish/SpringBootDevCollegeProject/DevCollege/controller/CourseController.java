package com.jenish.SpringBootDevCollegeProject.DevCollege.controller;

import com.jenish.SpringBootDevCollegeProject.DevCollege.dto.CourseModel;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Course;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.CourseNotFoundException;
import com.jenish.SpringBootDevCollegeProject.DevCollege.serviceImpl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CourseController {
    @Autowired
    private CourseServiceImpl courseService;

    @PostMapping("/course/addCourse")
    public String addCourse(@RequestBody @Valid CourseModel courseModel) {
        return courseService.saveCourse(courseModel);
    }

    @PutMapping("/course/updateCourse/{courseId}")
    public String updateCourse(@RequestBody Course course,@PathVariable String courseId) throws CourseNotFoundException{
            return courseService.updateCourseById(course,courseId);
    }

    @GetMapping("/course/getAll")
    public List<Course> getAllCourses() throws CourseNotFoundException{
        return courseService.getCourses();
    }

    @GetMapping("/course/getCourse/{courseId}")
    public Course getCourseById(@PathVariable String courseId) throws CourseNotFoundException {
        return courseService.CourseById(courseId);
    }

    @DeleteMapping("/course/deleteCourse/{courseId}")
    public String deleteCourse(@PathVariable String courseId) throws CourseNotFoundException {
       return courseService.deleteCourseById(courseId);
    }
}
