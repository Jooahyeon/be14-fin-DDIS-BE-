package com.ddis.ddis_hr.member;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        String rawPassword = "1234";
        String encoded = new BCryptPasswordEncoder().encode(rawPassword);
        System.out.println("암호화된 비밀번호: " + encoded);
    }
}