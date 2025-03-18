package com.noteseva.service;

import com.noteseva.exception.EmailSendingException;
import com.noteseva.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private OTPService otpService;

    @Autowired
    private UserRepository userRepo;

    private static final String fromMail = "noteseva1308@gmail.com";

    @Async
    public void sendOTP(String email){

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String otp = otpService.generateOTP(email);
            String name=userRepo.findUsersByEmail(email).getName();

            String htmlContent = "<div style='font-family: Arial, sans-serif; padding: 15px; border: 1px solid #ddd; border-radius: 10px;'>"
                    + "<h2 style='color: #2c3e50;'>One-Time Password (OTP) Verification</h2>"
                    + "<p style='font-size: 16px;'>Dear "+name+",</p>"
                    + "<p style='font-size: 16px;'>Your OTP for email verification is: "
                    + "<strong style='color: #e74c3c; font-size: 20px;'>" + otp + "</strong></p>"
                    + "<p style='font-size: 14px; color: #7f8c8d;'>This OTP is valid for 1 minutes. Do not share it with anyone.</p>"
                    + "<p style='margin-top: 20px;'>Regards,<br><strong>NOTESEVA Team</strong></p>"
                    + "</div>";

            helper.setTo(email);
            helper.setSubject("One-Time Password (OTP) Verification");
            helper.setText(htmlContent, true);
            helper.setFrom(fromMail);

            javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new EmailSendingException("Failed to send OTP email");
        }
    }

    @Async
    public void sendEmail(String mailTo) {
        try {
            String name=userRepo.findUsersByEmail(mailTo).getName();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailTo);
            message.setSubject(getEmailSubject());
            message.setText(getEmailBody(name));
            message.setFrom(fromMail);
            javaMailSender.send(message);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            throw new EmailSendingException("Failed to send OTP email");
        }
    }

    public String getEmailBody(String name)
    {
        return  "<div style='font-family: Arial, sans-serif; padding: 15px; border: 1px solid #ddd; border-radius: 10px;'>"
                + "<h2 style='color: #2c3e50;'>Welcome to NoteSeva!</h2>"
                + "<p style='font-size: 16px;'>Dear " + name + ",</p>"
                + "<p style='font-size: 16px;'>Your registration with <strong>NoteSeva</strong> has been successfully completed!</p>"
                + "<p style='font-size: 16px;'>We’re excited to have you on board. Now you can explore and access academic resources, including notes, previous year question papers, and project references.</p>"
                + "<p style='font-size: 14px; color: #7f8c8d;'>Please note: This is a system-generated email; replies to this email will not be monitored.</p>"
                + "<p style='margin-top: 20px;'>Thank you for joining us!<br><strong>Best regards,</strong><br><strong>Team NoteSeva</strong></p>"
                + "</div>";

    }

    public String getEmailSubject()
    {
        return "Registration Successful - NoteSeva";
    }
}