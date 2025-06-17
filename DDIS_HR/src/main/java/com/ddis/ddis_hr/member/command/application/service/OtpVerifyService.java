package com.ddis.ddis_hr.member.command.application.service;

import com.ddis.ddis_hr.member.command.domain.aggregate.entity.EmployeeMfaSecret;
import com.ddis.ddis_hr.member.command.domain.repository.EmployeeMfaSecretRepository;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OtpVerifyService {
    private final EmployeeMfaSecretRepository repo;
    private final GoogleAuthenticator gauth = new GoogleAuthenticator();

    public void verify(Long empId, int code) {
        EmployeeMfaSecret s = repo.findById(empId)
                .orElseThrow(() -> new IllegalStateException("MFA 미등록"));
        if (!gauth.authorize(s.getSecretB32(), code))
            throw new IllegalArgumentException("OTP 불일치");
        if (s.getVerifiedAt() == null) s.setVerifiedAt(LocalDateTime.now());
    }
}
