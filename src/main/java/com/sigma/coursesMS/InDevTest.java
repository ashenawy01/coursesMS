//package com.sigma.coursesMS;
//
//import com.sigma.coursesMS.service.EmailService;
//import jakarta.mail.MessagingException;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class InDevTest {
//
//    private static EmailService emailService = new EmailService();
//
//
//    public static void main(String[] args) {
//
//
//        try {
//            int otp = emailService.sendEmail("abdelrhman225328@bue.edu.eg");
//            System.out.println("the otp is : "  + otp);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//
//    }
//}
