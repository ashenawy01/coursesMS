package com.sigma.coursesMS.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Pattern;

// Application general configuration
@Configuration
public class AppConfig {

    /*
    * PasswordEncoder was moved from security configuration
    * coz it is injected in TeacherService
    * which is injected in Security
    * and that cause cycle injection error
    * Now it could be injected in security and service and others later on
    * */
    @Bean // Configure the Password encoder type (BCryptPasswordEncoder)
    public PasswordEncoder passwordEncoder() {
        // inception strength
        int strength = 10; // the higher the number, the more secure but slower the encryption
        return new BCryptPasswordEncoder(strength);
//        return new BCryptPasswordEncoder(strength) {
//            @Override
//            public String encode(CharSequence rawPassword) {
//                String password = rawPassword.toString();
//                // the regExep that includes all logic requirements (as the custom annotation)
//                Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%!]).{8,}$");
//                if (!pattern.matcher(password).matches()) {
//                    throw new IllegalArgumentException("Password does not meet complexity requirements");
//                }
//                return super.encode(rawPassword);
//            }
//        };
    };


    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("smartsigma111@gmail.com");
        mailSender.setPassword("***********"); // Set when you need
        mailSender.getJavaMailProperties().put("mail.smtp.auth", true);
        mailSender.getJavaMailProperties().put("mail.smtp.starttls.enable", true);
        return mailSender;
    }
}
