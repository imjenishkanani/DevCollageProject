package com.jenish.SpringBootDevCollegeProject.DevCollege.serviceImpl;

import com.jenish.SpringBootDevCollegeProject.DevCollege.Service.EnrolmentService;
import com.jenish.SpringBootDevCollegeProject.DevCollege.dto.EnrolmentModel;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Course;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Enrolment;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Student;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.CourseNotFoundException;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.EnrolmentNotFoundException;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.StudentNotFoundException;
import com.jenish.SpringBootDevCollegeProject.DevCollege.repository.CourseRepository;
import com.jenish.SpringBootDevCollegeProject.DevCollege.repository.EnrolmentRepository;
import com.jenish.SpringBootDevCollegeProject.DevCollege.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class EnrolmentServiceImpl implements EnrolmentService {

    @Autowired
    EnrolmentRepository enrolmentRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    @Override
    public String saveEnrolment(EnrolmentModel enrolmentModel) throws CourseNotFoundException, StudentNotFoundException {

        String studentId = enrolmentModel.getStudentId();
        String courseId = enrolmentModel.getCourseId();

        Student student = studentRepository.findById(studentId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);
        Enrolment enrolment;
        if(course != null) {
            if(student != null) {
        Float studentWallet = student.getWalletAmount();
        Float feesOfCourse = course.getCourseFees();
        Integer slotsOfCourse = course.getNoOfRegAllowed();
        Float walletAfterEnrol;
        Integer slotsAfterEnrol;

        List<Enrolment> allEnroll = new ArrayList<>();
        List<Enrolment> otherEnrolments = new ArrayList<>();

        allEnroll = enrolmentRepository.findAll();

        int cnt=0;
        int cnt1=0;
        for(Enrolment e : allEnroll) {
            if(e.getStudentId().equals(enrolmentModel.getStudentId() ))
            {
                    if (e.getCourseId().equals(enrolmentModel.getCourseId())) {
                        cnt++;

                    }

                        if(enrolmentModel.getCourseStartDateTime().isAfter(e.getCourseStartDateTime())&&
                                enrolmentModel.getCourseStartDateTime().isBefore(e.getCourseEndDateTime())) {
                            cnt1++;
                        }
            }
        }
                if (cnt > 0) {
                    return "You have already enrolled in Course Id: " + enrolmentModel.getCourseId();
                } else {
                    try {

                        if (slotsOfCourse > 0) {
                    if (!(cnt1 > 0)) {

                            if (studentWallet >= feesOfCourse) {
                                enrolment = enrolmentRepository.save(EnrolmentModel.modelToEntity(enrolmentModel));
                                walletAfterEnrol = studentWallet - feesOfCourse;
                                slotsAfterEnrol = slotsOfCourse - 1;

                                student.setWalletAmount(walletAfterEnrol);
                                course.setNoOfRegAllowed(slotsAfterEnrol);
                                enrolment.setCourseStatus("Allocated");


                                ZonedDateTime sdt = enrolmentModel.getCourseStartDateTime();
                                ZonedDateTime edt = sdt.plusDays(course.getCourseDuration());
                                enrolment.setCourseEndDateTime(edt);


                                String viewCourseLink = "http://localhost:8080/course/getCourse/";
                                String viewStudentLink = "http://localhost:8080/student/getStudent/";

                                viewCourseLink = viewCourseLink + enrolment.getCourseId();
                                viewStudentLink = viewStudentLink + enrolment.getStudentId();

                                enrolment.setCourseLink(viewCourseLink);
                                enrolment.setStudentLink(viewStudentLink);

                                studentRepository.save(student);
                                courseRepository.save(course);

                                return "Successfully Enrolled for " + student.getStudentName() + " in Course name " + course.getCourseName() + " for enrolment id " + enrolment.getEnrolmentId();
                            } else {
                                return "You are not allowed enrol in this course!!";
                            }
                    } else {
                        return "Already enroled in other course in this course time duration";
                    }
                        } else {
                            return "Sorry... All Slots are filled!!";
                        }
                    } catch (Exception e) {
                        return "Failed to enrol for this course!!";
                    }
                }
            } else {
                throw new StudentNotFoundException("Student Id: " + studentId + " doesn't exist.");
            }
        } else {
            throw new CourseNotFoundException("Course Id: " + courseId + " doesn't exist.");
        }
    }

    @Override
    public Enrolment enrolmentById(String enrolmentId) throws EnrolmentNotFoundException {
        Enrolment enrolment = enrolmentRepository.findByEnrolmentId(enrolmentId);
        if(enrolment != null) {
            return enrolment;
        } else {
            throw new EnrolmentNotFoundException("Enrolment Id: " + enrolmentId + " doesn't exist.");
        }
    }

    @Override
    public List<Enrolment> getAllEnrolment() throws EnrolmentNotFoundException {
        List<Enrolment> allEnrolments = new ArrayList<Enrolment>();

        allEnrolments = enrolmentRepository.getAllEnrolments();
        if(allEnrolments.size() != 0) {
            return allEnrolments;
        } else {
            throw new EnrolmentNotFoundException("No data found!!!");
        }
    }

    @Override
    public List<Enrolment> enrolmentOfStudentById(String studentId) throws StudentNotFoundException {
        List<Enrolment> enrolmentsOfStudent = new ArrayList<Enrolment>();
        List<Enrolment> listWithLink = new ArrayList<>();

        String viewCourseLink = "http://localhost:8080/course/getCourse/";
        String viewStudentLink = "http://localhost:8080/student/getStudent/";

        Student student = studentRepository.findById(studentId).orElse(null);
        if(student != null) {
            enrolmentsOfStudent = student.getAllEnrolments();

            for(Enrolment enrolment: enrolmentsOfStudent) {
                if(enrolment.getCourseStatus().equals("Allocated")) {
                    viewCourseLink = viewCourseLink + enrolment.getCourseId();
                    viewStudentLink = viewStudentLink + enrolment.getStudentId();

                    enrolment.setCourseLink(viewCourseLink);
                    enrolment.setStudentLink(viewStudentLink);

                    listWithLink.add(enrolment);

                    enrolmentRepository.save(enrolment);
                }
             }
            return listWithLink;
        } else {
            throw new StudentNotFoundException("Student Id: " + studentId + " does not enrol for any course.");
        }
    }

    @Override
    public String removeEnrolmentById(String enrolmentId) throws EnrolmentNotFoundException {
        Enrolment enrolment = enrolmentRepository.findById(enrolmentId).orElse(null);

        if(enrolment != null) {
            enrolmentRepository.deleteById(enrolmentId);
            return "removed";
        } else {
            throw new EnrolmentNotFoundException("Enrolment Id: " + enrolmentId + " doesn't exist.");
        }
    }

    @Override
    public String updateStatus(String enrolmentId) throws EnrolmentNotFoundException{
        Enrolment enrolment = enrolmentRepository.findById(enrolmentId).orElse(null);

        if(enrolment != null) {

            ZonedDateTime courseStartDay = enrolment.getCourseStartDateTime();

            enrolment.setCourseStatus("Cancelled");
            ZonedDateTime currentDt = ZonedDateTime.now();

            Duration durationOfCancel = Duration.between(currentDt, courseStartDay);

            Student student = studentRepository.findById(enrolment.getStudentId()).orElse(null);
            Course course = courseRepository.findById(enrolment.getCourseId()).orElse(null);

            Float refundAmount;
            Float courseAmount;
            Float studentWallet;
            Float walletAfterCancelCourse;
            Integer slots = 0;

            if (durationOfCancel.toHours() >= 48) {
                courseAmount = course.getCourseFees();
                studentWallet = student.getWalletAmount();
                slots = course.getNoOfRegAllowed();

                refundAmount = studentWallet + courseAmount;
                student.setWalletAmount(refundAmount);
                enrolment.setCourseStatus("Cancelled");

                course.setNoOfRegAllowed(slots + 1);

                enrolmentRepository.save(enrolment);
                studentRepository.save(student);
                courseRepository.save(course);

                return "Successfully change the status from Allocated to Cancelled for Enrolment Id: " + enrolmentId;
            } else if (durationOfCancel.toHours() >= 24) {
                courseAmount = course.getCourseFees();
                studentWallet = student.getWalletAmount();
                slots = course.getNoOfRegAllowed();

                refundAmount = (courseAmount * 70) / 100;


                walletAfterCancelCourse = studentWallet + refundAmount;


                student.setWalletAmount(walletAfterCancelCourse);
                enrolment.setCourseStatus("Cancelled");

                course.setNoOfRegAllowed(slots + 1);

                enrolmentRepository.save(enrolment);
                studentRepository.save(student);
                courseRepository.save(course);


                return "Successfully change the status from Allocated to Cancelled for Enrolment Id: " + enrolmentId;
            } else if (durationOfCancel.toHours() >= 12) {
                courseAmount = course.getCourseFees();
                studentWallet = student.getWalletAmount();
                slots = course.getNoOfRegAllowed();

                refundAmount = (courseAmount * 50) / 100;
                walletAfterCancelCourse = studentWallet + refundAmount;

                student.setWalletAmount(walletAfterCancelCourse);
                enrolment.setCourseStatus("Cancelled");

                course.setNoOfRegAllowed(slots + 1);

                enrolmentRepository.save(enrolment);
                studentRepository.save(student);
                courseRepository.save(course);

                return "Successfully change the status from Allocated to Cancelled for Enrolment Id: " + enrolmentId;
            } else {
                enrolment.setCourseStatus("Cancelled");
                course.setNoOfRegAllowed(slots + 1);
                enrolmentRepository.save(enrolment);
                courseRepository.save(course);
                return "Successfully change the status from Allocated to Cancelled for Enrolment Id: " + enrolmentId;
            }
        } else {
            throw new EnrolmentNotFoundException("Enrolment Id: " + enrolmentId + " doesn't exist.");
        }
    }

    @Override
    public String checkCourseAvailability(String courseId) throws CourseNotFoundException {
        Course course = courseRepository.findById(courseId).orElse(null);
        if(course != null) {
            if (course.getNoOfRegAllowed() >= 1) {
                return courseId + " : " + course.getCourseName() + " available for enrolment. " +
                        "Total " + course.getNoOfRegAllowed() + " slots available";
            } else {
                return courseId + " : " + course.getCourseName() + " is not available for enrolment";
            }
        } else {
            throw new CourseNotFoundException("Course Id: " + courseId + " doesn't exist.");
        }
    }

    @Override
    public Map<String, String> courseSuggest(String studentId) throws StudentNotFoundException{
        Student student = studentRepository.findById(studentId).orElse(null);
        List<Course> allCourses = new ArrayList<>();
        Map<String, String> courseSuggessionMap = new HashMap<>();

        allCourses = courseRepository.findAll();

        String courseTagLowerCase;
        String qualificationLowerCase;

        if(student != null) {
            for (Course course : allCourses) {
                courseTagLowerCase = course.getCourseTag().toLowerCase();
                qualificationLowerCase = student.getHighestQualification().toLowerCase();

                if (courseTagLowerCase.contains(qualificationLowerCase)) {
                    courseSuggessionMap.put(course.getCourseId(), course.getCourseName());
                }
            }
            return courseSuggessionMap;
        } else {
            throw new StudentNotFoundException("Student Id: " + studentId + " doesn't exist.");
        }
    }
}

