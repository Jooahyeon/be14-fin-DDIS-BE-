package com.ddis.ddis_hr.organization.command.domain.aggregate.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name = "team")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "team_name", length = 100, nullable = false)
    private String teamName;

    @Column(name = "team_code", length = 20, nullable = false, unique = true)
    private String teamCode;

    // ManyToOne: 여러 팀이 하나의 부서에 속함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentEntity department;

}
