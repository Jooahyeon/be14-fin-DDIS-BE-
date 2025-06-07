package com.ddis.ddis_hr.organization.command.domain.aggregate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name = "`position`") // position은 예약어이므로 백틱 처리
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PositionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_id")
    private Long positionId;

    @Column(name = "position_name", length = 100, nullable = false)
    private String positionName;

    @Column(name = "position_code", length = 20, nullable = false, unique = true)
    private String positionCode;

}
