package com.jenish.SpringBootDevCollegeProject.DevCollege.dto;

import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Course;
import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Enrolment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrolmentModel {

    private String enrolmentId;
    @NotNull(message = "Course Id is required")
    private String courseId;
    @NotNull(message = "Student Id is required")
    private String studentId;
    @NotNull(message = "Course start date is required")
    private ZonedDateTime courseStartDateTime;
    //@NotNull(message = "Course end date is required")
    private ZonedDateTime courseEndDateTime;
    //@NotNull(message = "Course status is required")
    private String courseStatus;


    public static Enrolment modelToEntity(EnrolmentModel model) {
        Enrolment entity = new Enrolment();
        //entity.setCourseId(id.toString());
        //id++;
        entity.setCourseId(model.getCourseId());
        entity.setStudentId(model.getStudentId());
        if (model.getCourseStartDateTime() != null) {
            entity.setCourseStartDateTime(ZonedDateTime.ofInstant(model.getCourseStartDateTime().toInstant(), ZoneId.systemDefault()));
        }
        //if (model.getCourseEndDateTime() != null) {
         //   entity.setCourseStartDateTime(ZonedDateTime.ofInstant(model.getCourseEndDateTime().toInstant(), ZoneId.systemDefault()));
        //}
        //entity.setCourseStatus(model.getCourseStatus());

        return entity;
    }
}
