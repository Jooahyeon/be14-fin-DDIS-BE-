package com.ddis.ddis_hr.member.command.domain.aggregate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name="tbl_user_info")
public class UserInfoEntity {
    @Id
    @Column(name = "user_code")
    private int userCode;

    @Column(name = "user_total_pt")
    private int userTotalPt;

    @Column(name = "user_level")
    private int userLevel;

    @Column(name = "user_nickname")
    private String userNickname;

    @Column(name = "user_susp_end_dt")
    private java.util.Date userSuspEndDt;

    @Column(name = "user_rpt_cnt")
    private int userRptCnt;

    @Column(name = "user_role")
    private String userRole;
}
