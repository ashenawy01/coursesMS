package com.sigma.coursesMS.controller;

import com.sigma.coursesMS.dto.NewPasswordDTO;
import com.sigma.coursesMS.dto.SignUpTeacherDTO;
import com.sigma.coursesMS.entity.Teacher;
import com.sigma.coursesMS.service.EmailService;
import com.sigma.coursesMS.service.TeacherService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/*
* Authentication controller
* Handles the endpoints of sign-up, sign-in and reset password
* Last update 2/5/2023
* */
@Controller
public class AuthController {

    private final TeacherService teacherService; // Inject Teacher service
    private final EmailService emailService; // Inject Email service (handle mail sender)


    // constructor injection
    public AuthController(TeacherService teacherService, EmailService emailService) {
        this.teacherService = teacherService;
        this.emailService = emailService;
    }

    //  ----------- Login -----------
    @GetMapping("/login") // customized login form
    public String showLoginForm(Model model) {
        return "login";
    }




    //  ----------- Sign up -----------

    /*
    * signUpTeacherDTO object is sent instead of Teacher itself
    * to avoid the errors that is caused by the custom validators (UniquePassword)
    * during other transaction such as reset password (saving the new object)
    */
    @GetMapping("/signup") // sign up page
    public String showSignUpPage(Model model) {
        // add signUpTeacherDTO object to handle validation in sign-up
        model.addAttribute("signUpTeacherDTO",new SignUpTeacherDTO());
        return "sign-up";
    }

    @PostMapping("/signup") // sign up - post request
    public String signup(@Valid SignUpTeacherDTO signUpTeacherDTO, // returned object
                         BindingResult result, // includes validation result
                         Model model) {

        if (result.hasErrors()) { // check validation error
            return "sign-up"; // Send the signup page again with errors of validation
        }
        // Create a new teacher object out of the input data (with no ID)
        Teacher teacher = createTeacher(signUpTeacherDTO);
        // saving the input new object into database (newTeacher has ID)
        Teacher newTeacher = teacherService.save(teacher);
        if (newTeacher == null) { // An error (exception has been thrown) while saving
            return "redirect:/signup";
        }

        return "redirect:/login"; // if user saved successfully
    }

    // Build Teacher object out of the sign-up input from the form
    // No need to check any data cause all handled in validation process (@Valid)
    private Teacher createTeacher(SignUpTeacherDTO signUpTeacherDTO) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(signUpTeacherDTO.getFirstName());
        teacher.setLastName(signUpTeacherDTO.getLastName());
        teacher.setPassword(signUpTeacherDTO.getPassword());
        teacher.setUsername(signUpTeacherDTO.getUsername());
        return teacher;
    }



    //  ----------- Reset password -----------

    // get reset password page
    @GetMapping("/reset-password")
    public String showResetPass (){
        return "reset-password";
    }

    @PostMapping("/reset-password") // Handle POST request of reset password page
    public String submitResetPass(@RequestParam String username, Model model, HttpSession session) {

        try {
            // find the required user (if existed or exception otherwise)
            Teacher teacher = teacherService.findByUsername(username);
            // send and get the generated otp (the email is hard coded for testing)
            int otp = emailService.sendEmail(username);
            // Store the and username OTP in the session
            session.setAttribute("resetPasswordOtp", otp);
            session.setAttribute("username", teacher.getUsername());

            // Redirect to the OTP form
            return "redirect:/enter-otp";

        } catch (MessagingException e) { // error with sending the email
            throw new RuntimeException("Unexpected error with sending email. please, try again");
        } catch (UsernameNotFoundException usernameNotFoundException) {
            model.addAttribute("errorMessage", "Username is Not existed");
        }

        return "reset-password"; // in case of any exception thrown
    }

    @GetMapping("/enter-otp") // OTP form
    public String getOtpForm () {
        return "otp-form";
    }


    @PostMapping("/enter-otp") // handle the entered OTP
    public String submitOTO(@RequestParam String otp, Model model, HttpSession session) {

        // Get the stored OTP from session
        int generatedOTP = (int) session.getAttribute("resetPasswordOtp");

        // Get the input OTP
        int inputOTP = Integer.parseInt(otp);

        if (inputOTP == generatedOTP) { // check the OTP
            // Allow adding new password
            return "redirect:/new-password";
        } else {
            // Display the error message
            model.addAttribute("errorMessage", "Incorrect OTP");
        }
        return "otp-form"; // in case of any error
    }

    @GetMapping("/new-password") // New password page
    public String getNewPassword (Model model) {
        // Pass the NewPasswordDTO object to the form to include and validate the data
        model.addAttribute("newPasswordDTO", new NewPasswordDTO());
        return "new-password";
    }


    @PostMapping("/new-password") // new password POST request
    public String setNewPassword (@Valid NewPasswordDTO newPasswordDTO, // Get and validate
                                  BindingResult result,
                                  Model model,
                                  HttpSession session) {
        // check if any validation error
        if (result.hasErrors()) {
            return "new-password"; // stay in new-password page and send validation error
        }
        // check similarity of the 2 passwords
        if (!newPasswordDTO.getPassword().equals(newPasswordDTO.getConfirmPassword())) {
            // Add error message to be displayed
            model.addAttribute("errorMessage", "The two passwords are not the same");
            return "new-password"; // stay in new-password page and send validation error
        }

        // Get the required username from system
        String username = (String) session.getAttribute("username");

        // update the password of user by Username (Exception handled in TeacherService)
        Teacher newTeacher = teacherService.updatePasswordByUsername(username, newPasswordDTO.getPassword());

        return "redirect:/login"; // success!
    }

}
