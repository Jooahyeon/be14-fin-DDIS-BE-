package com.ddis.ddis_hr.member.command.domain.repository;

import com.ddis.ddis_hr.member.command.domain.aggregate.entity.PwResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface PwResetTokenRepository extends JpaRepository<PwResetToken, Long> {
    /* ① 아직 안 쓰였고 만료 안 된 토큰 가져오기 */
    @Query("""
        SELECT t FROM PwResetToken t
        WHERE t.tokenId = :id
          AND t.consumedAt IS NULL
          AND t.expiresAt  > CURRENT_TIMESTAMP
    """)
    Optional<PwResetToken> findValid(UUID id);

    /* ② 청소용 */
    @Modifying
    @Query("""
        DELETE FROM PwResetToken t
        WHERE (t.expiresAt  < :cutoff)
           OR (t.consumedAt < :cutoff)
    """)
    void purge(LocalDateTime cutoff);
}
