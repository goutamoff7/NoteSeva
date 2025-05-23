package com.noteseva.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;

@Slf4j
@Service
public class OTPService {
    @Autowired
    private RedisService redisService;
    // 6 Digit otp
    public String generateOTP(String email) {
        String storedOtp = redisService.getOTP(email);
        if(storedOtp!=null)
            return storedOtp;
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        redisService.saveOTP(email, otp);
        log.info("{} : {}", email, otp);
        return otp;
    }

    public boolean validateOTP(String email, String userOtp) {
        String storedOtp = redisService.getOTP(email);
        if (storedOtp != null && storedOtp.equals(userOtp)) {
            redisService.deleteOTP(email);
            redisService.markEmailAsVerified(email);
            log.info("verified Email : {}",email);
            return true;
        }
        return false;
    }
}
