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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
    @Id
    @GenericGenerator(name = "courseId",strategy = "com.jenish.SpringBootDevCollegeProject.DevCollege.IdGenerator.GenerateCourseId")
    @GeneratedValue(
            generator = "courseId"
    )
    private String courseId;
    private String courseName;
    private String courseDescription;
    private Integer noOfRegAllowed;
    private Float courseFees;
    private Integer courseDuration;
    private String courseTag;

    @OneToMany(targetEntity = Enrolment.class, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "courseId",
            referencedColumnName = "courseId"
    )
    private List<Enrolment> allEnrolments;
}
