package com.jenish.SpringBootDevCollegeProject.DevCollege.Service;

import com.jenish.SpringBootDevCollegeProject.DevCollege.dto.StudentModel;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Student;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.StudentNotFoundException;

import java.util.List;
import java.util.Map;

public interface StudentService {
    public String saveStudent(StudentModel studentModel);
    public String deleteStudentById(String studenId) throws StudentNotFoundException;
    public String updateStudentById(Student student, String studentId) throws StudentNotFoundException;
    public Student StudentById(String studentId) throws StudentNotFoundException;
    public List<Student> getStudents() throws StudentNotFoundException;
    public String walletAmount(String studentId, Student AmountRequest) throws StudentNotFoundException;
    public Map<String,String> getWallet(String studentId) throws StudentNotFoundException;
}
