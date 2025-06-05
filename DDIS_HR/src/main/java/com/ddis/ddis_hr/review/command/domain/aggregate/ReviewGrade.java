package com.ddis.ddis_hr.review.command.domain.aggregate;

import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "review_grade")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_grade_id")
    private Long reviewGradeId;

    @Column(name = "review_min_score")
    private Integer reviewMinScore;

    @Column(name = "review_max_score")
    private Integer reviewMaxScore;

    @Column(name = "review_grade")
    private String reviewGrade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employeeId;
}
