package com.jenish.SpringBootDevCollegeProject.DevCollege.dto;

import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StudentModel {
    private String studentId;
    @NotNull(message = "Student Name is required")
    private String studentName;
    @NotNull(message = "Highest Qualification is required")
    private String highestQualification;
    @NotNull(message = "Student Contact number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Contact number 10-digit numeric")
    private String studentContactNo;
    @Positive
    @Min(value = 1)
    private Float walletAmount;

    public static Student ModelToEntity(StudentModel studentModel) {
        Student studentEntity = new Student();

        studentEntity.setStudentName(studentModel.getStudentName());
        studentEntity.setHighestQualification(studentModel.getHighestQualification());
        studentEntity.setStudentContactNo(studentModel.getStudentContactNo());
        studentEntity.setWalletAmount(studentModel.getWalletAmount());

        return studentEntity;
    }
}
