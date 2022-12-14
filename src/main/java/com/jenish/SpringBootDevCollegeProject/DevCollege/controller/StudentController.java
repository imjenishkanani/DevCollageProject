package com.jenish.SpringBootDevCollegeProject.DevCollege.controller;

import com.jenish.SpringBootDevCollegeProject.DevCollege.Service.StudentService;
import com.jenish.SpringBootDevCollegeProject.DevCollege.dto.StudentModel;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Student;
import com.jenish.SpringBootDevCollegeProject.DevCollege.exception.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;
    @PostMapping("/student/addStudent")
    public String addStudent(@RequestBody @Valid StudentModel studentModel) {
        return studentService.saveStudent(studentModel);
    }
    @DeleteMapping("/student/deleteStudent/{studentId}")
    public String deleteStudent(@PathVariable String studentId) throws StudentNotFoundException {
        return studentService.deleteStudentById(studentId);
    }
    @PutMapping("/student/updateStudent/{studentId}")
    public String updateStudent(@RequestBody Student student, @PathVariable String studentId) throws StudentNotFoundException {
        return studentService.updateStudentById(student,studentId);
    }
    @GetMapping("/student/getStudent/{studentId}")
    public Student getStudentById(@PathVariable String studentId) throws StudentNotFoundException {
        return studentService.StudentById(studentId);
    }
    @GetMapping("/student/getAll")
    public List<Student> getAllCourses() throws StudentNotFoundException{
        return studentService.getStudents();
    }
    @PostMapping("/student/StudentWallet/{studentId}")
    public String addWalletAmount(@Valid @PathVariable String studentId, @RequestBody Student amountRequest) throws StudentNotFoundException{
        return studentService.walletAmount(studentId, amountRequest);
    }
    @GetMapping("/student/getStudentWallet/{studentId}")
    public Map<String, String> getWalletAmount(@PathVariable String studentId) throws StudentNotFoundException{
        return studentService.getWallet(studentId);
    }
}
