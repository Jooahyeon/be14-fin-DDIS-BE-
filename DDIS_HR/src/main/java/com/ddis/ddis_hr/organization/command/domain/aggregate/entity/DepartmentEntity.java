package com.ddis.ddis_hr.organization.command.domain.aggregate.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "department")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "department_name", length = 100, nullable = false)
    private String departmentName;

    @Column(name = "department_code", length = 20, nullable = false, unique = true)
    private String departmentCode;

    // ManyToOne: 여러 부서가 하나의 본부에 속함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "head_id", nullable = false)
    private HeadquartersEntity headquarters;

    // OneToMany: 한 부서에 여러 팀이 속함
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamEntity> team = new ArrayList<>();
}
