package com.jenish.SpringBootDevCollegeProject.DevCollege.dto;

import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StudentModel {

    private String studentId;
    @NotBlank(message = "Student Name is required")
    private String studentName;
    @NotBlank(message = "Highest Qualification is required")
    private String highestQualification;
    @NotBlank(message = "Student Contact number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Contact number 10-digit numeric")
    private String studentContactNo;
    @Positive
    @Min(value = 0)
    private Double walletAmount;

    public static Student ModelToEntity(StudentModel studentModel) {
        Student studentEntity = new Student();

        studentEntity.setStudentName(studentModel.getStudentName());
        studentEntity.setHighestQualification(studentModel.getHighestQualification());
        studentEntity.setStudentContactNo(studentModel.getStudentContactNo());
        studentEntity.setWalletAmount(studentModel.getWalletAmount());

        return studentEntity;
    }
}
