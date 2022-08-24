package com.jenish.SpringBootDevCollegeProject.DevCollege.serviceImpl;

import com.jenish.SpringBootDevCollegeProject.DevCollege.Service.CourseService;
import com.jenish.SpringBootDevCollegeProject.DevCollege.dto.CourseModel;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Course;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.CourseNotFoundException;
import com.jenish.SpringBootDevCollegeProject.DevCollege.repository.CourseRepository;
import com.jenish.SpringBootDevCollegeProject.DevCollege.repository.EnrolmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrolmentRepository enrolmentRepository;

    @Override
    public String saveCourse(CourseModel courseModel) {
        try {
            int existCourseCount = courseRepository.isCourseAlreadyExist(courseModel.getCourseName());

            if (existCourseCount > 0) {
                return "Course: [" + courseModel.getCourseName() + "] is already exist!!";
            } else {
                Course course = courseRepository.save(CourseModel.modelToEntity(courseModel));
                return "******** Successfully added course detail for Course Id: " + course.getCourseId() + " ********";
            }
        } catch (Exception e) {
            return "******** Failed to add course details!!! ******** ";
        }
    }

    @Override
    public String updateCourseById(Course course, String courseId) throws CourseNotFoundException {
        Course courseToBeUpdated = courseRepository.findById(courseId).orElse(null);
        if (courseToBeUpdated != null) {
            int courseAllocationCounter = 0;
            courseAllocationCounter = courseRepository.isCourseAllocated(courseId, "Allocated");

            if (courseAllocationCounter > 0) {
                courseToBeUpdated.setCourseName(course.getCourseName());
                courseToBeUpdated.setCourseDescription(course.getCourseDescription());
                courseToBeUpdated.setNoOfRegAllowed(course.getNoOfRegAllowed());

                courseRepository.save(courseToBeUpdated);
                return "******** Successfully updated course detail for Course: " + courseId +
                        " ********\nCourse is already allocated. So,only Course Name, Description, And Slots updated.";
            } else {
                courseToBeUpdated.setCourseName(course.getCourseName());
                courseToBeUpdated.setCourseDescription(course.getCourseDescription());
                courseToBeUpdated.setNoOfRegAllowed(course.getNoOfRegAllowed());
                courseToBeUpdated.setCourseFees(course.getCourseFees());
                courseToBeUpdated.setCourseDuration(course.getCourseDuration());
                courseToBeUpdated.setCourseTag(course.getCourseTag());

                courseRepository.save(courseToBeUpdated);
                return "******** Successfully updated course detail for Course: " + courseId + " ********";
            }
        } else {
            throw new CourseNotFoundException("Course Id: " + courseId + " doesn't exist.");
        }
    }

    @Override
    public List<Course> getCourses() throws CourseNotFoundException {
        List<Course> allCourses = new ArrayList<>();
        allCourses = courseRepository.getAllCourse();

        if (allCourses.size() != 0) {
            return courseRepository.getAllCourse();
        } else {
            throw new CourseNotFoundException("NO DATA FOUND!!!");
        }
    }

    @Override
    public Course CourseById(String courseId) throws CourseNotFoundException {
        Course course = courseRepository.findByCourseId(courseId);
        if (course != null) {
            return course;
        } else {
            throw new CourseNotFoundException("Course Id: " + courseId + " doesn't exist.");
        }
    }

    @Override
    public String deleteCourseById(String courseId) throws CourseNotFoundException {
        Course course = courseRepository.findByCourseId(courseId);

        if (course != null) {
            int courseAllocationCounter = 0;
            courseAllocationCounter = courseRepository.isCourseAllocated(courseId, "Allocated");
            if (courseAllocationCounter > 0) {
                return "******** You can not delete Course Id: " + courseId +
                        " ********\nCourse Id: " + courseId + " is already allocated to student.";
            } else {
                courseRepository.deleteCourseByCourseId(courseId);
                return "******** Course: " + courseId + " deleted successfully... ********";
            }
        } else {
            throw new CourseNotFoundException("Course Id: " + courseId + " doesn't exist.");
        }
    }
}