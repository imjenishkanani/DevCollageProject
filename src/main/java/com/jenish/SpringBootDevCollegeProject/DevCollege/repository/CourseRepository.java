package com.jenish.SpringBootDevCollegeProject.DevCollege.repository;

import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, String> {
    public Course findByCourseId(@Param("id") String id);
    @Query(nativeQuery = true, value = "Select * from course")
    public List<Course> getAllCourse();
    @Query(nativeQuery = true, value = "select COUNT(*) from course where course_name = ?1")
    public int isCourseAlreadyExist(@Param("courseName") String courseName);

    @Query(nativeQuery = true, value = "select COUNT(*) from enrolment where course_id = ?1 AND course_status = ?2")
    public int isCourseAllocated(@Param("courseId") String courseId, @Param("courseStatus") String courseStatus);
    public void deleteCourseByCourseId(@Param("courseId") String courseId);
}
