package com.jenish.SpringBootDevCollegeProject.DevCollege.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Id
    @GenericGenerator(name = "studentId", strategy = "com.jenish.SpringBootDevCollegeProject.DevCollege.IdGenerator.GenerateStudentId")
    @GeneratedValue(
            generator = "studentId"
    )
    private String studentId;
    private String studentName;
    private String highestQualification;
    private String studentContactNo;
    private Double walletAmount;

    @OneToMany(targetEntity = Enrolment.class, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "studentId",
            referencedColumnName = "studentId"
    )
    private List<Enrolment> allEnrolments;
}
