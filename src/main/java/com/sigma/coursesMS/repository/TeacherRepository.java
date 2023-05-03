package com.sigma.coursesMS.repository;

import com.sigma.coursesMS.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Teacher findTeacherByUsername(String username);
    Teacher findTeacherById(int id);
}
