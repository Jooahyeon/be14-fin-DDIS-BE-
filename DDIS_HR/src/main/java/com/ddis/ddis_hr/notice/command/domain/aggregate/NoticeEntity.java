package com.ddis.ddis_hr.notice.command.domain.aggregate;

import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long noticeId;

    @Column(name = "notice_content", nullable = false, columnDefinition = "TEXT")
    private String noticeContent;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "notice_type", nullable = false)
    private String noticeType;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false;

    // FK 매핑: employee 테이블의 employee_id 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private Long relatedId;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
