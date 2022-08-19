package com.jenish.SpringBootDevCollegeProject.DevCollege.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
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

    //@NotNull(message="Course Name is required")
    private String courseName;
    //@NotNull(message="Course Description is required")
    private String courseDescription;

    //@Min(value = 1)
    //@Pattern(regexp = "^[0-9]*$", message = "Number of Registration Allowed field accepts only numeric value")
    private Integer noOfRegAllowed;

    //@Min(value = 0)
    //@Pattern(regexp = "^[0-9]*$", message = "Course Fees accepts only numeric value")
    private Float courseFees;

    //@Positive(message = "Course Duration is required")
    //@Pattern(regexp = "^[0-9]*$", message = "Course Duration accepts only numeric value")
    private Integer courseDuration;

    //@NotNull(message = "Course Tag is required")
    private String courseTag;

    @OneToMany(targetEntity = Enrolment.class, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "courseId",
            referencedColumnName = "courseId"
    )
    private List<Enrolment> allEnrolments;

//    @NotNull(message="Course Name is required")
//    private String courseName;
//    @NotNull(message="Course Description is required")
//    private String courseDescription;
//
//    @Min(value = 1)
//    @Pattern(regexp = "^[0-9]*$", message = "Number of Registration Allowed field accepts only numeric value")
//    private Integer noOfRegAllowed;
//
//    @Min(value = 0)
//    @Pattern(regexp = "^[0-9]*$", message = "Course Fees accepts only numeric value")
//    private Float courseFees;
//
//    @Positive(message = "Course Duration is required")
//    @Pattern(regexp = "^[0-9]*$", message = "Course Duration accepts only numeric value")
//    private Integer courseDuration;
//    @NotNull(message = "Course Tag is required")
    //    private String courseTag;
}
