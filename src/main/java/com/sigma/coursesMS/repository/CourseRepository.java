package com.sigma.coursesMS.repository;

import com.sigma.coursesMS.entity.Course;
import com.sigma.coursesMS.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findCourseByTeacher(Teacher teacher);
}
