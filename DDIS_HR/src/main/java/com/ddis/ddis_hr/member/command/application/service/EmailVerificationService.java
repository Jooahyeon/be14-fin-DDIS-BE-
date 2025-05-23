package com.ddis.ddis_hr.member.command.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class EmailVerificationService {
    private final JavaMailSender mailSender;
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public EmailVerificationService(JavaMailSender mailSender, RedisTemplate<String, String> redisTemplate) {
        this.mailSender = mailSender;
        this.redisTemplate = redisTemplate;
    }

    public void sendVerificationEmail(String email) {
        // 예시: 6자리 난수 생성
        String verificationCode = UUID.randomUUID().toString().substring(0, 6);
        // Redis에 저장 (5분 동안 유효)
        redisTemplate.opsForValue().set("email_verification:" + email, verificationCode, 5, TimeUnit.MINUTES);
        // 이메일 전송
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1214alex@naver.com");
        message.setTo(email);
        message.setSubject("이메일 인증 코드");
        message.setText("인증 코드는: " + verificationCode);
        mailSender.send(message);
    }

    public boolean verifyEmailCode(String email, String code) {
        String savedCode = redisTemplate.opsForValue().get("email_verification:" + email);
        if (savedCode != null && savedCode.equals(code)) {
            redisTemplate.delete("email_verification:" + email);
            return true;
        }
        return false;
    }
}
