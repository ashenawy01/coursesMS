package com.sigma.coursesMS.controller;

import com.sigma.coursesMS.entity.Course;
import com.sigma.coursesMS.dto.SubmittedCourse;
import com.sigma.coursesMS.entity.Teacher;
import com.sigma.coursesMS.service.CourseService;
import com.sigma.coursesMS.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;



/*
 * Teacher controller
 * Handles the endpoints of manage courses plus, retrieve and update teacher account
 * Last update 29/4/2023
 * */
@Controller
public class TeacherController {

    private final TeacherService teacherService; // Inject TeacherService
    private final CourseService courseService; // Inject CourseService

    // Controller injection
    public TeacherController(TeacherService teacherService, CourseService courseService) {
        this.teacherService = teacherService;
        this.courseService = courseService;
    }




    // ------------- Course endpoints -------------



    // Courses page (Retrieve all courses)
    @GetMapping("/courses")
    public String getCourses (Authentication authentication, Model model) {
        // get the current logged teacher account
        Teacher teacher = teacherService.findByUsername(authentication.getName());
        // Find the courses that added eby that account
        List<Course> courses = courseService.findCourseByTeacher(teacher);
        model.addAttribute("courses", courses); // send a list of all courses
        return "home";
    }

    @GetMapping("/addcourse") // Get Create Course Form
    public String addCoursePage (Model model) {
        return "create-course";
    }

    @PostMapping("/courses") // Create a new course
    public String createCourse (@ModelAttribute("submittedCourse") SubmittedCourse submittedCourse, // CourseDTo
                                @RequestParam("imgSrc") MultipartFile file, // Uploaded image file
                                Authentication authentication) {
        if (!file.isEmpty()) { // not in validation coz it could be with no image
            try { // Try add the new image to the folder (images) "Internal storage"
                String fileName = file.getOriginalFilename(); // Saving name
                // saving path
                String filePath = System.getProperty("user.dir") + "/src/main/resources/static/images/" + fileName;
                file.transferTo(new File(filePath)); // save
            } catch (IOException e) { // If cannot save (Do nothing)
                System.out.println("Image is not saved");
                e.printStackTrace();
            }
        }
        // get the current logged Teacher
        Teacher teacher = teacherService.findByUsername(authentication.getName());
        // Create the new course (Not including ID)
        Course course = createCourse(submittedCourse, teacher, file);
        // Save the new course and get the saved course (With ID)
        course = courseService.save(course);

        return "redirect:/courses"; // Success!
    }

    // Build a course out of the submitted form
    private Course createCourse (SubmittedCourse submittedCourse, Teacher courseTeacher, MultipartFile imgFile) {
        Course course = new Course();
        String fileName = imgFile.getOriginalFilename(); // image name
        course.setImgSrc(fileName);
        course.setName(submittedCourse.getName());
        course.setRate(submittedCourse.getRate());
        course.setHours(submittedCourse.getHours());
        course.setTeacher(courseTeacher);
        return course;
    }

    // Delete course by ID
    @DeleteMapping("/courses/{id}")
    public String getCourses (@PathVariable int id, Authentication authentication) {
        Teacher teacher = teacherService.findByUsername(authentication.getName());
        boolean isCourseDeleted = courseService.delete(id, teacher); // return true if deleted
        return "redirect:/courses";
    }







    // ------------- Account endpoints -------------


    @GetMapping("/account") // Get My-Account page
    public String showAccountPage(Authentication authentication, Model model) {
        Teacher teacher = teacherService.findByUsername(authentication.getName());
        model.addAttribute("teacher", teacher);
        return "account";
    }


    @PostMapping("/account") // Updating the account info
    public String updateUser (@ModelAttribute("teacher") @Valid Teacher teacher,
                              BindingResult bindingResult,
                              Model model,
                              Authentication authentication) {
        if (bindingResult.hasErrors()) { //check for validation
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "account";
        }
        // Get the current account (to update) from session
        Teacher existedTeacher = teacherService.findByUsername(authentication.getName());
        // Update the account
        Teacher newTeacher = teacherService.update(existedTeacher, teacher);
        return "redirect:/account";
    }


}
