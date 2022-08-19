package com.jenish.SpringBootDevCollegeProject.DevCollege.Service;

import com.jenish.SpringBootDevCollegeProject.DevCollege.dto.CourseModel;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Course;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.CourseNotFoundException;

import java.util.List;

public interface CourseService {
    public String saveCourse(CourseModel courseModel);
    public String updateCourseById(Course course,String courseId) throws CourseNotFoundException;
    public List<Course> getCourses() throws CourseNotFoundException;
    public Course CourseById(String courseId) throws CourseNotFoundException;
    public String deleteCourseById(String courseId) throws CourseNotFoundException;


}
