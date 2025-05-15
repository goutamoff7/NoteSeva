package com.noteseva.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final long OTP_EXPIRATION_TIME=600L;//10 minutes

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
                "verified:" + email, "true");
    }

    public boolean getAndDeleteEmailVerified(String email) {
        String verified = redisTemplate.opsForValue().getAndDelete("verified:" + email);
        return verified != null && verified.equals("true");
    }
}

