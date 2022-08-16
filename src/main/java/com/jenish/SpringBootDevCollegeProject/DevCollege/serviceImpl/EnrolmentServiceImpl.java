package com.jenish.SpringBootDevCollegeProject.DevCollege.serviceImpl;

import com.jenish.SpringBootDevCollegeProject.DevCollege.Service.EnrolmentService;
import com.jenish.SpringBootDevCollegeProject.DevCollege.dto.EnrolmentModel;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Enrolment;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Student;
import com.jenish.SpringBootDevCollegeProject.DevCollege.repository.CourseRepository;
import com.jenish.SpringBootDevCollegeProject.DevCollege.repository.EnrolmentRepository;
import com.jenish.SpringBootDevCollegeProject.DevCollege.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Optional<Student> student = studentRepository.findById(studentId);
        Float studentWallet = student.get().getWalletAmount();

        try {
            if(studentWallet > ) {

            }
                enrolmentRepository.save(EnrolmentModel.modelToEntity(enrolmentModel));
                //System.out.println(enrolmentModel.getCourseId());
                return "Successfully Enrolled for in Course name for enrolment id";
//            } else {
//                return "id not present";
//            }
        } catch(Exception e) {
            return "Failed to enrol for this course!!";
        }
    }
}
