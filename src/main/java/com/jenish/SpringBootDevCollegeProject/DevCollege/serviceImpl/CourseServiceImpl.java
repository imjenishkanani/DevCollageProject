package com.jenish.SpringBootDevCollegeProject.DevCollege.serviceImpl;
//import org.modelmapper.ModelMapper;
import com.jenish.SpringBootDevCollegeProject.DevCollege.Service.CourseService;
import com.jenish.SpringBootDevCollegeProject.DevCollege.dto.CourseModel;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Course;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Enrolment;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.CourseNotFoundException;
import com.jenish.SpringBootDevCollegeProject.DevCollege.repository.CourseRepository;
import com.jenish.SpringBootDevCollegeProject.DevCollege.repository.EnrolmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

//    @Autowired
//    private ModelMapper modelMapper;
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrolmentRepository enrolmentRepository;

//    @Autowired
//    public void setModelMapper(ModelMapper modelMapper) {
//        this.modelMapper = modelMapper;
//    }

    @Override
    public String saveCourse(CourseModel courseModel) {
        try {
//            Course course = modelMapper.map(courseRequest, Course.class);
            List<Course> allCourses = new ArrayList<>();
            allCourses = courseRepository.findAll();
            System.out.println(allCourses);
            System.out.println(allCourses.contains(courseModel.getCourseName()));
            int cnt = 0;
            for(Course course : allCourses) {
                if(course.getCourseName().equals(courseModel.getCourseName())) {
                    cnt++;
                    break;
                }
            }

            if(cnt > 0) {
                return "Course: [" + courseModel.getCourseName() + "] is already exist!!";
            } else {
                Course course = courseRepository.save(CourseModel.modelToEntity(courseModel));
                return "******** Successfully added course detail for Course Id: " + course.getCourseId() + " ********";
            }
        } catch(Exception e) {
            return "******** Failed to add course details!!! ******** " +e.getMessage();
        }
//        throw new CourseNotFoundException("Faile to addc ou");
    }

    @Override
    public String updateCourseById(Course course, String courseId) throws CourseNotFoundException {
        //try {
            Course courseToBeUpdated = courseRepository.findById(courseId).orElse(null);
            if(courseToBeUpdated != null) {
                List<Enrolment> allEnrolments = new ArrayList<>();
                //List<Enrolment> courseIdEnrolments = new ArrayList<>();
                int courseAllocationCounter = 0;

                allEnrolments = enrolmentRepository.findAll();
                for(Enrolment enrolment : allEnrolments)
                {
                    System.out.println(courseAllocationCounter);
                    if(enrolment.getCourseId().equals(courseId) && enrolment.getCourseStatus().equals("Allocated")) {

                        courseAllocationCounter++;
                        System.out.println(courseAllocationCounter);
                        break;
                    }
                }

                if(courseAllocationCounter > 0) {
                    courseToBeUpdated.setCourseName(course.getCourseName());
                    courseToBeUpdated.setCourseDescription(course.getCourseDescription());
                    courseToBeUpdated.setNoOfRegAllowed(course.getNoOfRegAllowed());
//                    courseToBeUpdated.setCourseFees(course.getCourseFees());
//                    courseToBeUpdated.setCourseDuration(course.getCourseDuration());
//                    courseToBeUpdated.setCourseTag(course.getCourseTag());

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
    public List<Course> getCourses() throws CourseNotFoundException{
        List<Course> allCourses = new ArrayList<>();
        allCourses = courseRepository.findAll();

        if(allCourses != null) {
            return courseRepository.findAll();
        } else {
            throw new CourseNotFoundException("NO DATA FOUND!!!");
        }
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
            List<Enrolment> allEnrolments = new ArrayList<>();
            //List<Enrolment> courseIdEnrolments = new ArrayList<>();
            int courseAllocationCounter = 0;

            allEnrolments = enrolmentRepository.findAll();
            for(Enrolment enrolment : allEnrolments)
            {
                System.out.println(courseAllocationCounter);
                if(enrolment.getCourseId().equals(courseId) && enrolment.getCourseStatus().equals("Allocated")) {

                    courseAllocationCounter++;
                    System.out.println(courseAllocationCounter);
                    break;
                }
            }

            if(courseAllocationCounter > 0) {
                return "******** You can not delete Course Id: " + courseId +
                        " ********\nCourse Id: " + courseId + " is already allocated to student.";
            } else {

                courseRepository.deleteById(courseId);
                return "******** Course: " + courseId + " deleted successfully... ********";
            }
        } else {
            throw new CourseNotFoundException("Course Id: " + courseId + " doesn't exist.");
        }
    }
}