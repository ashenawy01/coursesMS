package com.sigma.coursesMS.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
public class PasswordPolicyValidator implements ConstraintValidator<PasswordPolicy, String> {

    @Override
    public void initialize(PasswordPolicy constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%!]).{8,}$");
        return password != null && pattern.matcher(password).matches();
    }
}
