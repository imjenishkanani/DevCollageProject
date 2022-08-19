package com.jenish.SpringBootDevCollegeProject.DevCollege.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrolment {

    @Id
    @GenericGenerator(name = "enrolmentId",strategy = "com.jenish.SpringBootDevCollegeProject.DevCollege.IdGenerator.GenerateEnrolmentId")
    @GeneratedValue(
            generator = "enrolmentId"
    )
    private String enrolmentId;
    private String courseId;
    private String studentId;
    private ZonedDateTime CourseStartDateTime;
    private ZonedDateTime CourseEndDateTime;
    private String CourseStatus;
    //i added 19-08
    private String courseLink;
    private String studentLink;
}
