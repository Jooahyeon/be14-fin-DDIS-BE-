package com.ddis.ddis_hr.member.command.domain.aggregate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee_mfa_secret")
@Getter
@Setter
@NoArgsConstructor           // JPA 필수
@AllArgsConstructor          // (Long id, String secret) 생성자
public class EmployeeMfaSecret {
    @Id
    private Long employeeId;
    @Column(name="secret_b32",length = 32, columnDefinition = "CHAR(32) CHARACTER SET ascii COLLATE ascii_bin")
    private String secretB32;              // Base32
    private LocalDateTime verifiedAt;      // 앱 등록 완료 시각

    public EmployeeMfaSecret(Long employeeId, String secretB32) {
        this.employeeId = employeeId;
        this.secretB32 = secretB32;
    }
}
