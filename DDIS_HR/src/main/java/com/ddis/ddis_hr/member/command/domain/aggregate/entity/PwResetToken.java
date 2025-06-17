package com.ddis.ddis_hr.member.command.domain.aggregate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pw_reset_token")
@Getter
@Setter
@NoArgsConstructor
public class PwResetToken {
    @Id
    @Column(columnDefinition = "CHAR(36)")
    private UUID tokenId;

    private Long employeeId;

    @Column(length = 64, columnDefinition = "CHAR(64) CHARACTER SET ascii COLLATE ascii_bin")
    private String codeHash;

    private LocalDateTime expiresAt;
    private LocalDateTime consumedAt;

    /* 팩토리 메서드 */
    public static PwResetToken issue(Long empId, String sha256Hex, Duration ttl) {
        var t = new PwResetToken();
        t.tokenId   = UUID.randomUUID();
        t.employeeId = empId;
        t.codeHash  = sha256Hex;
        t.expiresAt = LocalDateTime.now().plus(ttl);
        return t;
    }

    public boolean usable() {
        return consumedAt == null && expiresAt.isAfter(LocalDateTime.now());
    }
}
