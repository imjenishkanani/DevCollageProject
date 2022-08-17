package com.jenish.SpringBootDevCollegeProject.DevCollege.serviceImpl;
//import org.modelmapper.ModelMapper;
import com.jenish.SpringBootDevCollegeProject.DevCollege.Service.CourseService;
import com.jenish.SpringBootDevCollegeProject.DevCollege.dto.CourseModel;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Course;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.CourseNotFoundException;
import com.jenish.SpringBootDevCollegeProject.DevCollege.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

//    @Autowired
//    private ModelMapper modelMapper;
    @Autowired
    private CourseRepository courseRepository;

//    @Autowired
//    public void setModelMapper(ModelMapper modelMapper) {
//        this.modelMapper = modelMapper;
//    }

    @Override
    public String saveCourse(CourseModel courseModel) {
        try {

//            Course course = modelMapper.map(courseRequest, Course.class);

            Course course = courseRepository.save(CourseModel.modelToEntity(courseModel));
            return "******** Successfully added course detail for Course Id: " + course.getCourseId() + " ********";
        } catch(Exception e) {
            return "******** Failed to add course details!!! ******** " +e.getMessage();
        }
    }

    @Override
    public String updateCourseById(Course course, String courseId) throws CourseNotFoundException {
        //try {
            Course courseToBeUpdated = courseRepository.findById(courseId).orElse(null);
            if(courseToBeUpdated != null) {

                courseToBeUpdated.setCourseName(course.getCourseName());
                courseToBeUpdated.setCourseDescription(course.getCourseDescription());
                courseToBeUpdated.setNoOfRegAllowed(course.getNoOfRegAllowed());
                courseToBeUpdated.setCourseFees(course.getCourseFees());
                courseToBeUpdated.setCourseDuration(course.getCourseDuration());
                courseToBeUpdated.setCourseTag(course.getCourseTag());

                courseRepository.save(courseToBeUpdated);
                return "******** Successfully updated course detail for Course: " + courseId + " ********";
            } else {
                throw new CourseNotFoundException("Course Id: " + courseId + " doesn't exist.");
            }
       // } catch(Exception e) {
         //   return "Failed to update course detail!";
        //}
//        private String courseName;
//        private String courseDescription;
//        private Integer noOfRegAllowed;
//        private Float courseFees;
//        private Integer courseDuration;
//        private String courseTag;
    }

    @Override
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course CourseById(String courseId) throws CourseNotFoundException{
        Course course = courseRepository.findById(courseId).orElse(null);
        if(course != null) {
            return course;
        } else {
            throw new CourseNotFoundException("Course Id: " + courseId + " doesn't exist.");
        }
    }

    @Override
    public String deleteCourseById(String courseId) throws CourseNotFoundException {
        Course course = courseRepository.findById(courseId).orElse(null);
//        try {
//            if(courseRepository.existsById(courseId)) {
//
//                courseRepository.deleteById(courseId);
//                return "******** Course: " + courseId + " deleted successfully... ********";
//            } else {
//                return "******** Course: " + courseId + " doesn't exist!!! ********";
//            }
//        } catch(Exception e) {
//            return "Failed to delete course detail!";
//        }
        if(course != null) {
            courseRepository.deleteById(courseId);
            return "******** Course: " + courseId + " deleted successfully... ********";
        } else {
            throw new CourseNotFoundException("Course Id: " + courseId + " doesn't exist.");
        }
    }
}