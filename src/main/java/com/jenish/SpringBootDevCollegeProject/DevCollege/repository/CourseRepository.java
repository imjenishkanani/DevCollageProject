package com.jenish.SpringBootDevCollegeProject.DevCollege.repository;

import com.jenish.SpringBootDevCollegeProject.DevCollege.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface CourseRepository extends JpaRepository<Course, String> {
   public Course findByCourseId(@Param("id") String id);
   @Query(nativeQuery=true, value="Select * from course")
   public List<Course> getAllCourse();
   public void deleteCourseByCourseId(@Param("courseId") String courseId);
}
