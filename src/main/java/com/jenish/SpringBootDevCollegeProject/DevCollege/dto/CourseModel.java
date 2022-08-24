package com.jenish.SpringBootDevCollegeProject.DevCollege.dto;

import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseModel {

    private String courseId;

    @NotBlank(message = "Course Name is required")
    private String courseName;

    @NotNull(message = "Course Description is required")
    private String courseDescription;

    @Min(value = 1)
    @NotNull(message = "Number of Registration Allowed field is required")
    private Integer noOfRegAllowed;

    @Min(value = 0)
    @NotNull(message = "Course Fees is required")
    private Float courseFees;

    @Positive(message = "Please enter valid course duration.")
    @NotNull(message = "Course Duration is required")
    private Integer courseDuration;

    @NotNull(message = "Course Tag is required")
    private String courseTag;

    public static Course modelToEntity(CourseModel model) {
        Course entity = new Course();

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
