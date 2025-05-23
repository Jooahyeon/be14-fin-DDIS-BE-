package com.ddis.ddis_hr.member.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name="department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "department_name", nullable = false, length = 255)
    private String departmentName;

    @Column(name = "department_code", nullable = false, length = 255)
    private String departmentCode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "head_id", nullable = false)
    private Headquarters headquarters;
}
