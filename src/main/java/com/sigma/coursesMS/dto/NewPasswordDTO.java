package com.sigma.coursesMS.dto;

import com.sigma.coursesMS.validator.PasswordPolicy;
import jakarta.validation.constraints.NotEmpty;
/*
* DTO used to reset password
* fot transferring the input of the bew passwords
* and validating them
* */
public class NewPasswordDTO {

    @PasswordPolicy // include the password policy
    private String password;

    // must be similar logic is implemented in controller
    @NotEmpty(message = "Re-enter the new password")
    private String confirmPassword;

    public NewPasswordDTO(String password, String confirmPassword) {
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
    public NewPasswordDTO() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "NewPasswordDTO{" +
                "password='" + password + '\'' +
                ", getPassword='" + confirmPassword + '\'' +
                '}';
    }
}
