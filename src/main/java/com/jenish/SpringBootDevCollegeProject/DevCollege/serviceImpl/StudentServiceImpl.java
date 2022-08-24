package com.jenish.SpringBootDevCollegeProject.DevCollege.serviceImpl;

import com.jenish.SpringBootDevCollegeProject.DevCollege.Service.StudentService;
import com.jenish.SpringBootDevCollegeProject.DevCollege.dto.StudentModel;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Course;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Enrolment;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Student;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.StudentNotFoundException;
import com.jenish.SpringBootDevCollegeProject.DevCollege.repository.CourseRepository;
import com.jenish.SpringBootDevCollegeProject.DevCollege.repository.EnrolmentRepository;
import com.jenish.SpringBootDevCollegeProject.DevCollege.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrolmentRepository enrolmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public String saveStudent(StudentModel studentModel) {
        try {
            Student student = studentRepository.save(StudentModel.ModelToEntity(studentModel));
            return "******** Successfully added student detail for Student Id: " + student.getStudentId() + " ********";
        } catch (Exception e) {
            return "******** Failed to add student details!!! ******** " + e.getMessage();
        }
    }

    @Override
    public String updateStudentById(Student student, String studentId) throws StudentNotFoundException {
        Student studentToBeUpdated = studentRepository.findById(studentId).orElse(null);
        if (studentToBeUpdated != null) {

            studentToBeUpdated.setStudentName(student.getStudentName());
            studentToBeUpdated.setHighestQualification(student.getHighestQualification());
            studentToBeUpdated.setStudentContactNo(student.getStudentContactNo());

            studentRepository.save(studentToBeUpdated);
            return "******** Successfully updated student detail for Course: " + studentId + " ********";
        } else {
            throw new StudentNotFoundException("Course Id: " + studentId + " doesn't exist.");
        }
    }

    @Override
    public String deleteStudentById(String studenId) throws StudentNotFoundException {
        Student student = studentRepository.findById(studenId).orElse(null);
        List<Enrolment> allEnrolments = enrolmentRepository.findAll();
        Student enroledStudent;
        Course course;
        int cnt = 0;
        Float totalFeesOfEnrolledCourse = 0.0f;
        Float feesToBeRefund;


        if (student != null) {
            for (Enrolment enrolment : allEnrolments) {
                if (enrolment.getStudentId().equals(studenId)) {
                    enrolment.setCourseStatus("Cancelled");
                    String enroledCourseId = enrolment.getCourseId();
                    course = courseRepository.findById(enroledCourseId).orElse(null);

                    totalFeesOfEnrolledCourse = totalFeesOfEnrolledCourse + course.getCourseFees();
                }
            }
            feesToBeRefund = totalFeesOfEnrolledCourse / 2;

            studentRepository.deleteById(studenId);
            return "Successfully deleted student detail for Student Id: " + studenId + " Amount RS. " + feesToBeRefund +
                    " will be refunded in original payment method in 24 hours.";
        } else {
            throw new StudentNotFoundException("Student Id: " + studenId + " doesn't exist.");
        }
    }

    @Override
    public Student StudentById(String studentId) throws StudentNotFoundException {
        Student student = studentRepository.findByStudentId(studentId);
        if (student != null) {
            return student;
        } else {
            throw new StudentNotFoundException("Student Id: " + studentId + " doesn't exist.");
        }
    }

    @Override
    public List<Student> getStudents() throws StudentNotFoundException {
        List<Course> allStudents = new ArrayList<>();
        allStudents = courseRepository.findAll();

        if (allStudents != null) {
            return studentRepository.findAll();
        } else {
            throw new StudentNotFoundException("NO DATA FOUND!!!");
        }
    }

    @Override
    public String walletAmount(String studentId, Student amountRequest) throws StudentNotFoundException {

        Student student = studentRepository.findById(studentId).orElse(null);
        Double walletAmt;
        if (student != null) {
            if (amountRequest.getWalletAmount() > 0) {
                walletAmt = student.getWalletAmount();
                walletAmt = walletAmt + amountRequest.getWalletAmount();
                student.setWalletAmount(walletAmt);
                studentRepository.save(student);
                return "******** Successfully added amount for Student Id: " + student.getStudentId() + " and available balance is RS. " + student.getWalletAmount() + " ********";
            } else {
                return "Please enter only positive amount!!";
            }
        } else {
            throw new StudentNotFoundException("Student Id: " + amountRequest.getStudentId() + " doesn't exist.");
        }
    }

    @Override
    public Map<String, String> getWallet(String studentId) throws StudentNotFoundException {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student != null) {
            Map<String, String> studentWallet = new HashMap<>();
            studentWallet.put("studentId", student.getStudentId());
            studentWallet.put("amount", student.getWalletAmount().toString());
            return studentWallet;
        } else {
            throw new StudentNotFoundException("Student Id: " + studentId + " doesn't exist.");
        }
    }
}