package com.jenish.SpringBootDevCollegeProject.DevCollege.dto;

import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseModel {

    //private static Integer id = 1;

    private String courseId;
    @NotNull(message="Course Name is required")
    private String courseName;

    @NotNull(message="Course Description is required")
    private String courseDescription;

    @Min(value = 1)
    @Pattern(regexp = "[0-9]", message = "Number of Registration Allowed field accepts only numeric value")
    @NotNull(message="Number of Registration Allowed field is required")
    private Integer noOfRegAllowed;

    @Min(value = 0)
    //@Pattern(regexp = "[0-9]", message = "Course Fees accepts only numeric value")
    @NotNull(message="Course Fees is required")
    private Float courseFees;

    @Positive(message = "Course Duration is required")
    //@Pattern(regexp = "^[0-9]*$", message = "Course Duration accepts only numeric value")
    @NotNull(message="Course Duration is required")
    private Integer courseDuration;
    @NotNull(message = "Course Tag is required")
    private String courseTag;

    public static Course modelToEntity(CourseModel model) {
        Course entity = new Course();
        //entity.setCourseId(id.toString());
        //id++;
        entity.setCourseName(model.getCourseName());
        entity.setCourseDescription(model.getCourseDescription());
        entity.setNoOfRegAllowed(model.getNoOfRegAllowed());
        entity.setCourseFees(model.getCourseFees());
        entity.setCourseDuration(model.getCourseDuration());
        entity.setCourseTag(model.getCourseTag());
        entity.setCourseDescription(model.getCourseDescription());
        return entity;
    }
}
