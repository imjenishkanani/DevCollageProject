package com.jenish.SpringBootDevCollegeProject.DevCollege.serviceImpl;

import com.jenish.SpringBootDevCollegeProject.DevCollege.Service.StudentService;
import com.jenish.SpringBootDevCollegeProject.DevCollege.dto.StudentModel;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Course;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Student;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.CourseNotFoundException;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.StudentNotFoundException;
import com.jenish.SpringBootDevCollegeProject.DevCollege.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public String saveStudent(StudentModel studentModel) {

        //try {
        Student student =  studentRepository.save(StudentModel.ModelToEntity(studentModel));
        return "******** Successfully added student detail for Student Id: " + student.getStudentId() + " ********";
        //} catch(Exception e) {
        //  return "******** Failed to add student details!!! ******** " +e.getMessage();
        // }
    }

    @Override
    public String updateStudentById(Student student, String studentId) throws StudentNotFoundException {
        //try {
        Student studentToBeUpdated = studentRepository.findById(studentId).orElse(null);
        if (studentToBeUpdated != null) {

            studentToBeUpdated.setStudentName(student.getStudentName());
            studentToBeUpdated.setHighestQualification(student.getHighestQualification());
            studentToBeUpdated.setStudentContactNo(student.getStudentContactNo());
            studentToBeUpdated.setWalletAmount(student.getWalletAmount());


            studentRepository.save(studentToBeUpdated);
            return "******** Successfully updated student detail for Course: " + studentId + " ********";
        } else {
            throw new StudentNotFoundException("Course Id: " + studentId + " doesn't exist.");
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
    public String deleteStudentById(String studenId) throws StudentNotFoundException {
        Student student = studentRepository.findById(studenId).orElse(null);
        if (student != null) {
            studentRepository.deleteById(studenId);
            return "******** Successfully deleted student detail for Student Id: " + student + " ********";
        } else {
            throw new StudentNotFoundException("Student Id: " + studenId + " doesn't exist.");
        }
    }

    @Override
    public Student StudentById(String studentId) throws StudentNotFoundException {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student != null) {
            return student;
        } else {
            throw new StudentNotFoundException("Student Id: " + studentId + " doesn't exist.");
        }
    }

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public String walletAmount(String studentId, Student AmountRequest) throws StudentNotFoundException{

        Student student  = studentRepository.findById(studentId).orElse(null);
        Float walletAmt;
        if(student != null) {
            walletAmt = student.getWalletAmount();
            walletAmt = walletAmt + AmountRequest.getWalletAmount();
            student.setWalletAmount(walletAmt);
            studentRepository.save(student);
            return "******** Successfully added amount for Student Id: " + student.getStudentId() + " and available balance is RS. " + student.getWalletAmount() + " ********";
        } else {
            throw new StudentNotFoundException("Student Id: " + AmountRequest.getStudentId() + " doesn't exist.");
        }
    }

    @Override
    public Map<String,String> getWallet(String studentId) throws StudentNotFoundException{
        Student student = studentRepository.findById(studentId).orElse(null);
        if(student != null) {
            Map<String, String> studentWallet = new HashMap<>();
            studentWallet.put("studentId", student.getStudentId());
            studentWallet.put("amount", student.getWalletAmount().toString());
            return studentWallet;
        } else {
            throw new StudentNotFoundException("Student Id: " + studentId + " doesn't exist.");
        }
    }
}