package com.noteseva.service;

import com.noteseva.exception.EmailSendingException;
import com.noteseva.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void sendOTP(String email, String otp) {

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("One-Time Password (OTP) Verification");
            helper.setText(getEmailBody(Integer.parseInt(otp)), true);
            helper.setFrom(fromMail);

            javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new EmailSendingException("Failed to send OTP");
        }
    }

    @Async
    public void sendSuccessEmail(String email,String name) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Registration Successful - NoteSeva");
            helper.setText(getEmailBody(name), true);
            helper.setFrom(fromMail);
            javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new EmailSendingException("Failed to send OTP email");
        }
    }

    public String getEmailBody(String name) {
        return "<div style='font-family: Arial, sans-serif; padding: 15px; border: 1px solid #ddd; border-radius: 10px;'>"
                + "<h2 style='color: #2c3e50;'>Welcome to NoteSeva!</h2>"
                + "<p style='font-size: 16px;'>Dear " + name + ",</p>"
                + "<p style='font-size: 16px;'>Your registration with <strong>NoteSeva</strong> has been successfully completed!</p>"
                + "<p style='font-size: 16px;'>We’re excited to have you on board. Now you can explore and access academic resources, including notes, previous year question papers, and project references.</p>"
                + "<p style='font-size: 14px; color: #7f8c8d;'>Please note: This is a system-generated email; replies to this email will not be monitored.</p>"
                + "<p style='margin-top: 20px;'>Thank you for joining us!<br><strong>Best regards,</strong><br><strong>Team NoteSeva</strong></p>"
                + "</div>";

    }

    public String getEmailBody(Integer otp) {
        return "<div style='font-family: Arial, sans-serif; padding: 15px; border: 1px solid #ddd; border-radius: 10px;'>"
                + "<h2 style='color: #2c3e50;'>One-Time Password (OTP) Verification</h2>"
                + "<p style='font-size: 16px;'>Dear User,</p>"
                + "<p style='font-size: 16px;'>Your OTP for email verification is: "
                + "<strong style='color: #e74c3c; font-size: 20px;'>" + otp + "</strong></p>"
                + "<p style='font-size: 14px; color: #7f8c8d;'>This OTP is valid for 10 minutes. Do not share it with anyone.</p>"
                + "<p style='margin-top: 20px;'>Regards,<br><strong>NOTESEVA Team</strong></p>"
                + "</div>";

    }

    @Async
    public void successfulPasswordChangingMail(String email, String name) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String htmlContent = "<div style='font-family: Arial, sans-serif; padding: 15px; border: 1px solid #ddd; border-radius: 10px;'>"
                    + "<h2 style='color: #2c3e50;'>Password Changed Successfully</h2>"
                    + "<p style='font-size: 16px;'>Dear " + name + ",</p>"
                    + "<p style='font-size: 16px;'>You have successfully changed your password. If this was you, no further action is needed.</p>"
                    + "<p style='font-size: 14px; color: #7f8c8d;'>If you did not request this change, please reset your password immediately or contact support.</p>"
                    + "<p style='margin-top: 20px;'>Regards,<br><strong>NoteSeva Team</strong></p>"
                    + "</div>";

            helper.setTo(email);
            helper.setSubject("Your Request is fulfilled now!!");
            helper.setText(htmlContent, true);
            helper.setFrom(fromMail);

            javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new EmailSendingException("Failed to send OTP email");
        }
    }

    @Async
    public void alertEmail(String email,String name) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String otp = otpService.generateOTP(email);

            helper.setTo(email);
            helper.setSubject(alterEmailSubject());
            helper.setText(alterEmailBody(otp, name), true);  // 'true' enables HTML rendering
            helper.setFrom(fromMail);

            javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new EmailSendingException("Failed to send Welcome email");
        }
    }

    public String alterEmailBody(String otp, String name) {
        return "<div style='font-family: Arial, sans-serif; padding: 20px; border: 1px solid #ddd; border-radius: 10px; background-color: #f9f9f9;'>"
                + "<h2 style='color: #d63031;'>⚠ Password Reset Attempt ⚠</h2>"
                + "<p style='font-size: 16px; color: #2c3e50;'>Dear " + name + ",</p>"
                + "<p style='font-size: 16px;'>We noticed a request to reset your password using the <strong>Forgot Password</strong> option.</p>"
                + "<p style='font-size: 16px;'>If this was you, please proceed with the next steps.</p>"
                + "<p style='color: #e74c3c; font-size: 14px;'>🚨 If you did not request this, please ignore this email and ensure your account security.</p>"
                + "<p style='font-size: 16px;'>Your OTP for password reset is: "
                + "<strong style='color: #e74c3c; font-size: 20px;'>" + otp + "</strong></p>"
                + "<p style='font-size: 14px; color: #7f8c8d;'>This OTP is valid for 1 minute. Do not share it with anyone.</p>"
                + "<p style='margin-top: 20px;'>Stay secure and vigilant!</p>"
                + "<p><strong>Best regards,</strong><br>Team Journal Application</p>"
                + "</div>";
    }

    public String alterEmailSubject() {
        return "🔐 Password Reset Alert – Your OTP is on the Way!";
    }
}
