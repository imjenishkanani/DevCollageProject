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
import java.time.ZonedDateTime;
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

        List<Enrolment> allEnroll = new ArrayList<>();
        List<Enrolment> otherEnrolments = new ArrayList<>();

        allEnroll = enrolmentRepository.findAll();

//        int cnt1=0;
//        for(Enrolment e : allEnroll) {
////            System.out.println(e.getStudentId());
////            System.out.println(enrolmentModel.getStudentId());
//            if(e.getStudentId().equals(enrolmentModel.getStudentId() ))
//            {
////                System.out.println(e.getCourseId());
////                System.out.println(enrolmentModel.getCourseId());
//                if (e.getCourseId() != enrolmentModel.getCourseId()) {
//                    cnt1++;
//                    break;
//                }
//            }
//        }

        int cnt=0;
        //int cnt1=0;
        for(Enrolment e : allEnroll) {
//            System.out.println(e.getStudentId());
//            System.out.println(enrolmentModel.getStudentId());
            if(e.getStudentId().equals(enrolmentModel.getStudentId() ))
            {
//                System.out.println(e.getCourseId());
//                System.out.println(enrolmentModel.getCourseId());
                    if (e.getCourseId().equals(enrolmentModel.getCourseId())) {
                        cnt++;
                        break;
                    }
//                    else {
//
//                        //otherEnrolments.add(e);
//                        if(enrolmentModel.getCourseStartDateTime().isAfter(e.getCourseStartDateTime())&&
//                                enrolmentModel.getCourseStartDateTime().isBefore(e.getCourseEndDateTime())) {
//                            cnt1++;
//                        }
//                    }
            }
        }

        // enrolled in other course in between


        if(cnt > 0) {
            return "You have already enrolled in Course Id: " + enrolmentModel.getCourseId();
        } else {
            try {

                if (slotsOfCourse > 0) {
//                    if (cnt1 > 0) {
//                        System.out.println("counter of already enrolled : " + cnt1);
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

                            //updating the course end date time
                            ZonedDateTime sdt = enrolmentModel.getCourseStartDateTime();
                            ZonedDateTime edt = sdt.plusDays(course.getCourseDuration());
                            enrolment.setCourseEndDateTime(edt);
                            System.out.println(edt);

                            //saving updates related wallet amount
                            studentRepository.save(student);

                            //saving updates related course slots
                            courseRepository.save(course);

                            return "Successfully Enrolled for " + student.getStudentName() + " in Course name " + course.getCourseName() + " for enrolment id " + enrolment.getEnrolmentId();
                        } else {
                            return "You are not allowed enrol in this course!!";
                        }
//                    } else {
//                        return "Already enroled in other course in this course time duration";
//                    }
                } else {
                    return "Sorry... All Slots are filled!!";
                }
            } catch (Exception e) {
                return "Failed to enrol for this course!!";
            }
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

    @Override
    public List<Enrolment> enrolmentOfStudentById(String studentId) throws StudentNotFoundException {
        List<Enrolment> enrolmentsOfStudent = new ArrayList<Enrolment>();
        List<Enrolment> listWithLink = new ArrayList<>();

        //Enrolment  = enrolmentRepository.findById(studentId).orElse(null);
        //if(enrolmentOfStudent != null) {
        String viewCourseLink = "http://localhost:8080/course/getCourse/";
        String viewStudentLink = "http://localhost:8080/student/getStudent/";

        Student student = studentRepository.findById(studentId).orElse(null);
        if(student != null) {
            enrolmentsOfStudent = student.getAllEnrolments();

            for(Enrolment enrolment: enrolmentsOfStudent) {
                if(enrolment.getCourseStatus().equals("Allocated")) {
                    viewCourseLink = viewCourseLink + enrolment.getCourseId();
                    viewStudentLink = viewStudentLink + enrolment.getStudentId();

                    System.out.println(viewStudentLink);
                    System.out.println(viewCourseLink);

                    enrolment.setCourseLink(viewCourseLink);
                    enrolment.setStudentLink(viewStudentLink);

                    listWithLink.add(enrolment);
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
    public String updateStatus(String enrolmentId) {
        // enrolment
        Enrolment enrolment = enrolmentRepository.findById(enrolmentId).orElse(null);
        ZonedDateTime courseStartDay = enrolment.getCourseStartDateTime();

        enrolment.setCourseStatus("Cancelled");
        ZonedDateTime currentDt = ZonedDateTime.now();

        Duration durationOfCancel = Duration.between(currentDt, courseStartDay);
        //System.out.println(durationOfCancel.toHours());

        // student
        Student student = studentRepository.findById(enrolment.getStudentId()).orElse(null);
        // course
        Course course = courseRepository.findById(enrolment.getCourseId()).orElse(null);

        Float refundAmount;
        Float courseAmount;
        Float studentWallet;
        Float walletAfterCancelCourse;
        Integer slots = 0;

        if(durationOfCancel.toHours() >= 48) {
            courseAmount = course.getCourseFees();
            studentWallet = student.getWalletAmount();
            slots = course.getNoOfRegAllowed();

            refundAmount = studentWallet + courseAmount;
            student.setWalletAmount(refundAmount);
            enrolment.setCourseStatus("Cancelled");

            course.setNoOfRegAllowed(slots+1);

            enrolmentRepository.save(enrolment);
            studentRepository.save(student);
            courseRepository.save(course);

            return "Successfully change the status from Allocated to Cancelled for Enrolment Id: " + enrolmentId;
        } else if(durationOfCancel.toHours() >= 24) {
            courseAmount = course.getCourseFees();
            studentWallet = student.getWalletAmount();
            slots = course.getNoOfRegAllowed();

            refundAmount = (courseAmount * 70) / 100;
            System.out.println(refundAmount);

            walletAfterCancelCourse = studentWallet + refundAmount;
            System.out.println(walletAfterCancelCourse);

            student.setWalletAmount(walletAfterCancelCourse);
            enrolment.setCourseStatus("Cancelled");

            course.setNoOfRegAllowed(slots+1);

            enrolmentRepository.save(enrolment);
            studentRepository.save(student);
            courseRepository.save(course);


            return "Successfully change the status from Allocated to Cancelled for Enrolment Id: " + enrolmentId;
        } else if(durationOfCancel.toHours() >= 12) {
            courseAmount = course.getCourseFees();
            studentWallet = student.getWalletAmount();
            slots = course.getNoOfRegAllowed();

            refundAmount = (courseAmount * 50) / 100;
            walletAfterCancelCourse = studentWallet + refundAmount;

            student.setWalletAmount(walletAfterCancelCourse);
            enrolment.setCourseStatus("Cancelled");

            course.setNoOfRegAllowed(slots+1);

            enrolmentRepository.save(enrolment);
            studentRepository.save(student);
            courseRepository.save(course);

            return "Successfully change the status from Allocated to Cancelled for Enrolment Id: " + enrolmentId;
        } else {
            enrolment.setCourseStatus("Cancelled");
            course.setNoOfRegAllowed(slots+1);
            enrolmentRepository.save(enrolment);
            courseRepository.save(course);
            return "Successfully change the status from Allocated to Cancelled for Enrolment Id: " + enrolmentId;
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
        //System.out.println("course suggesion called");
        Student student = studentRepository.findById(studentId).orElse(null);
        List<Course> allCourses = new ArrayList<>();
        Map<String, String> courseSuggessionMap = new HashMap<>();

        allCourses = courseRepository.findAll();
        System.out.println("All course :" + allCourses.size());
        System.out.println(student);

        String courseTagLowerCase;
        String qualificationLowerCase;

        if(student != null) {
            for (Course course : allCourses) {
                //System.out.println("Course tag :" + course.getCourseTag() + " stud qualification : " + student.getHighestQualification());
                //System.out.println(course.getCourseTag());

                // to ignore case
                courseTagLowerCase = course.getCourseTag().toLowerCase();
                qualificationLowerCase = student.getHighestQualification().toLowerCase();

                if (courseTagLowerCase.contains(qualificationLowerCase)) {
                    System.out.println("inside.....");
                    courseSuggessionMap.put(course.getCourseId(), course.getCourseName());
                }
            }
            return courseSuggessionMap;
        } else {
            throw new StudentNotFoundException("Student Id: " + studentId + " doesn't exist.");
        }
    }
}

