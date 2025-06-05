package com.ddis.ddis_hr.goals.command.domain.aggregate;

import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "selfreview")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "selfreview_id")
    private Long id;

    @Column(name = "performance_value")
    private Double performanceValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "selfreview_status", length = 10)
    private Status status;

    @Column(name = "selfreview_created_at")
    private LocalDateTime createdAt;

    @Column(name = "selfreview_approve_date")
    private LocalDateTime approveDate;

    // goal_id FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id", nullable = false)
    private Goals goal;

    // 자기평가 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id_selfreviewer", nullable = false)
    private Employee selfReviewer;

    @Column(name = "selfreview_content", columnDefinition = "TEXT")
    private String selfreviewContent;

    // 오타 컬럼명 그대로 매핑
    @Column(name = "reviewer_scroe")
    private Integer reviewerScore;

    @Column(name = "reviewer_created_at")
    private LocalDateTime reviewerCreatedAt;

    @Column(name = "reviewer_content", columnDefinition = "TEXT")
    private String reviewerContent;

    // 평가자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id_reviewer")
    private Employee reviewer;

    public enum Status {
        대기, 승인, 반려
    }
}
