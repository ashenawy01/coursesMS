package com.sigma.coursesMS.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    private JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public int sendEmail(String username) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        int otp = generateOTP();
        helper.setTo(username + "bue.edu.eg"); // all emails will be made from bue usernames
        helper.setSubject("Reset Password");
        helper.setText("the OTP of + " + username + " is : " + otp, true);
        mailSender.send(message);
        return otp;
    }
    int generateOTP() {
        Random random = new Random();
        int min = 1000;
        int max = 9999;
        int randomNumber = random.nextInt(max - min + 1) + min;
        return randomNumber;
    }
}
