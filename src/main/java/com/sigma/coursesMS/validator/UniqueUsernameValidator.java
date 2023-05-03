package com.sigma.coursesMS.validator;

import com.sigma.coursesMS.service.TeacherService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private TeacherService teacherService;


    public UniqueUsernameValidator(TeacherService teacherService) {
        this.teacherService = teacherService;
    }


    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("before comparing");
        if (username == null) {
            return false;
        }
        boolean isValid = false;
        try {
            isValid = teacherService.findByUsername(username) == null;
        } catch (UsernameNotFoundException e) {
            return true; // User not existed which means the entered username is valid
        }
        return isValid;
    }


}
