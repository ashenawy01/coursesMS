package com.sigma.coursesMS.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;
    @Column(name ="name")
    private String name;
    @Column(name ="imgsrc")
    private String imgSrc;
    @Column(name ="hours")
    private double hours;
    @Column(name ="rate")
    private double rate;
    @ManyToOne
    @JoinColumn(name ="teacherID")
    private Teacher teacher;

    public Course(String name, String imgSrc, double hours, double rate, Teacher teacher) {
        this.name = name;
        this.imgSrc = imgSrc;
        this.hours = hours;
        this.rate = rate;
        this.teacher = teacher;
    }

    public Course() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
