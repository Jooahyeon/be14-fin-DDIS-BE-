package com.ddis.ddis_hr.member.command.application.scheduler;

import com.ddis.ddis_hr.member.command.domain.repository.PwResetTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PwTokenPurger {
    private final PwResetTokenRepository repo;

    @Scheduled(cron = "0 0 */6 * * *")
    @Transactional
    public void purge() {
        repo.purge(LocalDateTime.now().minusHours(0));
    }
}
