package com.jenish.SpringBootDevCollegeProject.DevCollege.serviceImpl;

import com.jenish.SpringBootDevCollegeProject.DevCollege.Service.EnrolmentService;
import com.jenish.SpringBootDevCollegeProject.DevCollege.dto.EnrolmentModel;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Course;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Enrolment;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Student;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.CourseNotFoundException;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.EnrolmentNotFoundException;
import com.jenish.SpringBootDevCollegeProject.DevCollege.repository.CourseRepository;
import com.jenish.SpringBootDevCollegeProject.DevCollege.repository.EnrolmentRepository;
import com.jenish.SpringBootDevCollegeProject.DevCollege.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnrolmentServiceImpl implements EnrolmentService {

    @Autowired
    EnrolmentRepository enrolmentRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    @Override
    public String saveEnrolment(EnrolmentModel enrolmentModel) {

        String studentId = enrolmentModel.getStudentId();
        String courseId = enrolmentModel.getCourseId();

        Student student = studentRepository.findById(studentId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);
        Enrolment enrolment;

        Float studentWallet = student.getWalletAmount();
        Float feesOfCourse = course.getCourseFees();
        Integer slotsOfCourse = course.getNoOfRegAllowed();
        Float walletAfterEnrol;
        Integer slotsAfterEnrol;

        try {
            if(slotsOfCourse > 0) {
                if (studentWallet >= feesOfCourse) {
                    enrolment = enrolmentRepository.save(EnrolmentModel.modelToEntity(enrolmentModel));
                    //System.out.println(enrolmentModel.getCourseId());
                    walletAfterEnrol = studentWallet - feesOfCourse;
                    slotsAfterEnrol = slotsOfCourse - 1;

                    //updating wallet amount
                    student.setWalletAmount(walletAfterEnrol);
                    //updating slots
                    course.setNoOfRegAllowed(slotsAfterEnrol);
                    //update course status
                    enrolment.setCourseStatus("Allocated");

                    //saving updates related wallet amount
                    studentRepository.save(student);
                    //saving updates related course slots
                    courseRepository.save(course);

                    return "Successfully Enrolled for " + student.getStudentName() + " in Course name " + course.getCourseName() + " for enrolment id " + enrolment.getEnrolmentId();
                } else {
                    return "You are not allowed enrol in this course!!";
                }
            } else {
                return "Sorry... All Slots are filled!!";
            }
        } catch(Exception e) {
            return "Failed to enrol for this course!!";
        }
    }

    @Override
    public Enrolment enrolmentById(String enrolmentId) throws EnrolmentNotFoundException {
        Enrolment enrolment = enrolmentRepository.findById(enrolmentId).orElse(null);

        if(enrolment != null) {
            return enrolment;
        } else {
            throw new EnrolmentNotFoundException("Enrolment Id: " + enrolmentId + " doesn't exist.");
        }
    }

    @Override
    public List<Enrolment> getAllEnrolment() throws EnrolmentNotFoundException {
        List<Enrolment> allEnrolments = new ArrayList<Enrolment>();
        allEnrolments = enrolmentRepository.findAll();
        if(allEnrolments != null) {
            return allEnrolments;
        } else {
            throw new EnrolmentNotFoundException("No data found!!!");
        }
    }
}



