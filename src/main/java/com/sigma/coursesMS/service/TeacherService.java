package com.sigma.coursesMS.service;

import com.sigma.coursesMS.entity.Teacher;
import com.sigma.coursesMS.repository.TeacherRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeacherService implements UserDetailsService {
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder cryptPasswordEncoder;


    public TeacherService(TeacherRepository teacherRepository, PasswordEncoder cryptPasswordEncoder) {
        this.teacherRepository = teacherRepository;
        this.cryptPasswordEncoder = cryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Teacher teacher = teacherRepository.findTeacherByUsername(username);
        if (teacher == null) {
            throw new UsernameNotFoundException("Teacher not found");
        }
        return new User(teacher.getUsername(), teacher.getPassword(), true, true, true, true,
                AuthorityUtils.createAuthorityList("ROLE_TEACHER"));
    }

    public Teacher findTeacherById (int id) throws Exception {
        Teacher teacher = (Teacher) teacherRepository.findTeacherById(id);
        if (teacher == null) {
            throw new  RuntimeException("Teacher not found");
        }
        return teacher;
    }

    public Teacher findByUsername(String name) throws UsernameNotFoundException{
        Teacher teacher = teacherRepository.findTeacherByUsername(name);
        if (teacher == null) {
            throw new UsernameNotFoundException("Teacher not found");
        }
        return teacher;
    }

    public Teacher save(Teacher teacher){
        if (teacher == null) {
            throw new RuntimeException("Null object exception");
        }
        String password = teacher.getPassword();
        Teacher newTeacher = null;
        try {
            String encodedPassword = cryptPasswordEncoder.encode(password);
            teacher.setPassword(encodedPassword);
            newTeacher = teacherRepository.save(teacher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newTeacher;
    }

    public Teacher update(Teacher oldTeacher, Teacher newTeacher) {
        oldTeacher.setFirstName(newTeacher.getFirstName());
        oldTeacher.setLastName(newTeacher.getLastName());
        oldTeacher.setUsername(newTeacher.getUsername());
        oldTeacher.setPassword(newTeacher.getPassword());

        Teacher finalTeacher = null;
        try {
            finalTeacher = teacherRepository.save(oldTeacher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalTeacher;
    }



    public Teacher updatePasswordByUsername(String username, String password) {
        Teacher teacher;
        try {
            teacher = teacherRepository.findTeacherByUsername(username);
        } catch (UsernameNotFoundException usernameNotFoundException) {
            throw new UsernameNotFoundException("User is not found to update password");
        }
        teacher.setPassword(cryptPasswordEncoder.encode(password));
        return teacherRepository.save(teacher);
    }


}
