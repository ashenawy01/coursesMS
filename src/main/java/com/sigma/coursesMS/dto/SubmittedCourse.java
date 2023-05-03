package com.sigma.coursesMS.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/*
 * DTO used in new course form
 * fot transferring the course date input
 * and validating them
 * */
public class SubmittedCourse {
    @NotBlank(message = "Name is required")
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name;
    private MultipartFile imgSrc;
    @NotBlank(message = "Name is required")
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private double hours;
    private double rate;

    public SubmittedCourse(){}
    public SubmittedCourse(String name, MultipartFile imgSrc, double hours, double rate) {
        this.name = name;
        this.imgSrc = imgSrc;
        this.hours = hours;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(MultipartFile imgSrc) {
        this.imgSrc = imgSrc;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
