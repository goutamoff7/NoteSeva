package com.noteseva.service;

import com.noteseva.components.OTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OTPService {

    @Autowired
    JavaMailSender javaMailSender;

    private Map<String,String> otpMap=new ConcurrentHashMap<>();
    private Map<String, Long> otpExpiryMap = new ConcurrentHashMap<>();
    Set<String> verifiedEmails = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public String generateOTP(String toEmail) {
        try {
            Random random = new Random();
            String otp = String.valueOf(random.nextInt(99999)+100000);
            otpMap.put(toEmail,otp);
            otpExpiryMap.put(toEmail, System.currentTimeMillis() + (10 * 60 * 1000)); //1 min valid.
            return otp;
        }catch(Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Failed to generate OTP. Please try again later.");
        }
    }

    public boolean verifyOTP(OTP request) {
        try {
            String email = request.getEmail();
            if (!otpMap.containsKey(email))
                throw new RuntimeException("OTP not generated for this email.");

            if (System.currentTimeMillis() > otpExpiryMap.get(email)) {
                otpMap.remove(email);
                otpExpiryMap.remove(email);
                throw new RuntimeException("OTP has expired");
            }
            if(request.getOtp().equals(otpMap.get(email))) {
                verifiedEmails.add(email);
                otpExpiryMap.remove(email);
                otpMap.remove(email);
                return true;
            }
            throw new RuntimeException("Invalid OTP entered");
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Scheduled(fixedRate = 15*60*1000)
    public void removeExpiredOTPs() {
        System.out.println("Running OTP cleanup at: " + new java.util.Date());
        long currentTime = System.currentTimeMillis();
        otpExpiryMap.entrySet().removeIf(entry -> {
            boolean isExpired = currentTime > entry.getValue();
            if (isExpired) {
                String email = entry.getKey();
                otpMap.remove(email);
                verifiedEmails.remove(email);
                System.out.println("Removed expired OTP for: " + email);
            }
            return isExpired;
        });
    }
}
