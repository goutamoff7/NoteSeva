package com.noteseva.service;

import com.noteseva.exception.EmailSendingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private static final String fromMail = "noteseva1308@gmail.com";

    @Async
    public void sendEmail(String mailTo) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailTo);
            message.setSubject(getEmailSubject());
            message.setText(getEmailBody());
            message.setFrom(fromMail);
            javaMailSender.send(message);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            throw new EmailSendingException("Failed to send OTP email");
        }
    }

    public String getEmailBody()
    {
        return "Your registration with NoteSeva has been successfully completed!\n" +
                "\n" +
                "Please note, this is a system-generated email—no replies will be monitored.\n" +
                "\n" +
                "Thank you for joining us!\n" +
                "\n" +
                "Best regards,\n" +
                "Team NoteSeva";
    }

    public String getEmailSubject()
    {
        return "Registration Successful - NoteSeva";
    }
}