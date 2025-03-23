package com.noteseva.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final long OTP_EXPIRATION_TIME=600L;//10 minutes
    private final long VERIFIED_EMAIL_EXPIRATION=86400L; //24 Hrs

    public void saveOTP(String email, String otp) {
        redisTemplate.opsForValue().set(
                "otp:" + email,
                otp,
                OTP_EXPIRATION_TIME,
                TimeUnit.SECONDS);
    }

    public String getOTP(String email) {
        return redisTemplate.opsForValue().get("otp:" + email);
    }

    public void deleteOTP(String email) {
        redisTemplate.delete("otp:" + email);
    }

    public void markEmailAsVerified(String email) {
        redisTemplate.opsForValue().set(
                "verified:" + email,
                "true",
                VERIFIED_EMAIL_EXPIRATION,
                TimeUnit.SECONDS);
    }

    public boolean isEmailVerified(String email) {
        return redisTemplate.opsForValue().get("verified:" + email) != null;
    }
}

