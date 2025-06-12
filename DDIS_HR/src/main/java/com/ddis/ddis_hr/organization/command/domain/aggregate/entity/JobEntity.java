package com.ddis.ddis_hr.organization.command.domain.aggregate.entity;

import jakarta.persistence.*;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Entity
@Table(name = "job",
        uniqueConstraints = @UniqueConstraint(columnNames = "job_code"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "job_name", nullable = false)
    private String jobName;

    @Column(name = "job_code", nullable = false, length = 50)
    private String jobCode;

    /**
     * JSON 배열을 문자열로 저장한다고 가정합니다.
     * 예) '["C++", "Unity", ...]' 와 같은 형태가 TEXT 컬럼에 들어갑니다.
     */
    @Lob
    @Column(name = "job_role", columnDefinition = "TEXT")
    private String jobRole;

    @Lob
    @Column(name = "job_need", columnDefinition = "TEXT")
    private String jobNeed;

    @Lob
    @Column(name = "job_necessary", columnDefinition = "TEXT")
    private String jobNecessary;

    @Lob
    @Column(name = "job_preference", columnDefinition = "TEXT")
    private String jobPreference;

    @Column(name = "team_id", nullable = false)
    private Long teamId;

    // TeamEntity와 연관 매핑(@ManyToOne)
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "team_id", insertable = false, updatable = false)
     private TeamEntity team;
}
