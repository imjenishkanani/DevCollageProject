package com.jenish.SpringBootDevCollegeProject.DevCollege.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.ZonedDateTime;

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
    private String courseLink;
    private String studentLink;
}
