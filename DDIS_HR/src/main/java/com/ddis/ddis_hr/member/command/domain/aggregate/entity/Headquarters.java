package com.ddis.ddis_hr.member.command.domain.aggregate.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name="headquarters")
public class Headquarters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "head_id")
    private Long headId;

    @Column(name = "head_name", nullable = false, length = 255)
    private String headName;

    @Column(name = "head_code", nullable = false, length = 255)
    private String headCode;
}
