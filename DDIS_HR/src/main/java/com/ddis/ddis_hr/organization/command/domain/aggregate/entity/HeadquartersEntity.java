package com.ddis.ddis_hr.organization.command.domain.aggregate.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "headquarters")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HeadquartersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "head_id")
    private Long headId;

    @Column(name = "head_name", length = 100, nullable = false)
    private String headName;

    @Column(name = "head_code", length = 20, nullable = false, unique = true)
    private String headCode;

    // OneToMany: 한 본부에 여러 부서가 속함
    @OneToMany(mappedBy = "headquarters", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DepartmentEntity> department = new ArrayList<>();
}
