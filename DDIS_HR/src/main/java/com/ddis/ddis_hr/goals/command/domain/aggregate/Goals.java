package com.ddis.ddis_hr.goals.command.domain.aggregate;

import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "goal")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Goals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private Long goalId;

    @Column(name = "goal_title")
    private String goalTitle;

    @Column(name = "goal_content")
    private String goalContent;

    @Column(name = "goal_value")
    private Integer goalValue;

    @Column(name = "goal_weight")
    private Integer goalWeight;

    @Column(name = "goal_created_at")
    private LocalDateTime goalCreatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employeeId;
}
