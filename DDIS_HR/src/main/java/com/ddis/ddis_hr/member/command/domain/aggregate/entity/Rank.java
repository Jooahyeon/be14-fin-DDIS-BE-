package com.ddis.ddis_hr.member.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name="rank")
public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")
    private Long rankId;

    @Column(name = "rank_name", nullable = false, length = 255)
    private String rankName;

    @Column(name = "rank_order", nullable = false)
    private Integer rankOrder;

    @Column(name = "rank_code", nullable = false, length = 255)
    private String rankCode;
}
