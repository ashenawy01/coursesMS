package com.sigma.coursesMS.service;

import com.sigma.coursesMS.entity.Course;
import com.sigma.coursesMS.entity.Teacher;
import com.sigma.coursesMS.repository.CourseRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    public final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    public List<Course> findCourseByTeacher(Teacher teacher) {
        if (teacher == null) {
            throw new RuntimeException("Teacher is null");
        }
        return courseRepository.findCourseByTeacher(teacher);
    }

    public Course save(Course newCourse) {
        return courseRepository.save(newCourse);
    }

    public Course findCourseById(int id) {
        return courseRepository.getById(id);
    }
    public boolean delete(int id, Teacher teacher) {
        Course course = findCourseById(id);
        if (teacher.getCourses().contains(course)) {
             try {
                 courseRepository.delete(course);
                 return true;
             } catch (Exception e) {
                 e.printStackTrace();
             }
        }
        return false;
    }
}
