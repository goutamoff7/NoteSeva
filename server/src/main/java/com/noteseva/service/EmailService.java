package com.noteseva.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendEmail(String to)
    {
        try{
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(getEmailSubject());
            mail.setText(getEmailBody());
            javaMailSender.send(mail);
        }catch(Exception e)
        {
            System.out.println("Mail sending exception");
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