package com.jenish.SpringBootDevCollegeProject.DevCollege.dto;

import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Enrolment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrolmentModel {

    private String enrolmentId;


    @NotNull(message = "Course Id is required")
    private String courseId;

    @NotNull(message = "Student Id is required")
    private String studentId;

    @FutureOrPresent(message = "Date must be a in present or in future time")
    @NotNull(message = "Course start date is required")
    private ZonedDateTime courseStartDateTime;

    private ZonedDateTime courseEndDateTime;
    private String courseStatus;


    public static Enrolment modelToEntity(EnrolmentModel model) {
        Enrolment entity = new Enrolment();

        entity.setCourseId(model.getCourseId());
        entity.setStudentId(model.getStudentId());
        if (model.getCourseStartDateTime() != null) {
            entity.setCourseStartDateTime(ZonedDateTime.ofInstant(model.getCourseStartDateTime().toInstant(), ZoneId.systemDefault()));
        }
        return entity;
    }
}
