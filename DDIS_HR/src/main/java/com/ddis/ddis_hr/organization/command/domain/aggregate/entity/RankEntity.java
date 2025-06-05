package com.ddis.ddis_hr.organization.command.domain.aggregate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name = "`rank`") // rank 또한 예약어일 수 있으니 백틱 처리
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RankEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")
    private Long rankId;

    @Column(name = "rank_name", length = 100, nullable = false)
    private String rankName;

    @Column(name = "rank_order", nullable = false)
    private Integer rankOrder;

    @Column(name = "rank_code", length = 20, nullable = false, unique = true)
    private String rankCode;
}
