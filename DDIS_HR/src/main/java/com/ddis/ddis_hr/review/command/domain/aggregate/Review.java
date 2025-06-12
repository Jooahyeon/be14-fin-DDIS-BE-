package com.ddis.ddis_hr.review.command.domain.aggregate;

import com.ddis.ddis_hr.goals.command.domain.aggregate.Performance;
import com.ddis.ddis_hr.member.command.domain.aggregate.entity.Employee;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "review")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "review_score")
    private Double reviewScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_grade_id")
    private ReviewGrade reviewGradeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selfreview_id")
    private Performance selfreviewId;


}
