package com.ddis.ddis_hr.organization.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "introduction")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class IntroductionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "introduction_id")
    private Long introductionId;

    @Column(name = "introduction_context", nullable = false, columnDefinition = "TEXT")
    private String introductionContext;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    /* 부서' 또는 '팀'으로 구분 */
    @Column(name = "introduction_type", nullable = false, length = 10)
    private String introductionType;

    /* department_id 또는 NULL */
    @Column(name = "department_id")
    private Long departmentId;

    /* team_id 또는 NULL */
    @Column(name = "team_id")
    private Long teamId;

    public IntroductionEntity(String introductionContext,
                              String introductionType,
                              Long departmentId,
                              Long teamId) {
        this.introductionContext = introductionContext;
        this.introductionType = introductionType;
        this.departmentId = departmentId;
        this.teamId = teamId;
        this.createdAt = LocalDate.now();
    }
}
