package com.sigma.coursesMS.dto;

import com.sigma.coursesMS.validator.PasswordPolicy;
import com.sigma.coursesMS.validator.UniqueUsername;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/*
 * DTO used in sign up and update account info
 * fot transferring the user date input
 * and validating them
 * */
public class SignUpTeacherDTO {
    @NotEmpty(message = "First name cannot be empty")
    @Size(min = 2, max = 50, message = "First name must include at least 2 letters and maximum 50")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    @Size(min = 2, max = 50, message = "Last name must include at least 2 letters and maximum 50")
    private String lastName;

    @UniqueUsername
    private String username;

    @PasswordPolicy
    private String password;

    public SignUpTeacherDTO(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public SignUpTeacherDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
