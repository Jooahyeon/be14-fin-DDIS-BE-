package com.ddis.ddis_hr.member.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name="tbl_personal_info")
@SecondaryTable(name = "tbl_user_info", pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_code", referencedColumnName = "user_code"))
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_code")
    private int userCode;                                                   //회원번호

    @Column(name="user_name", nullable=false)
    private String userName;                                                //회원이름

    @Column(name="user_id", nullable = false, unique = true)
    private String userId;                                                  //회원아이디(ID 개념)

                                              //회원비밀번호
    @Column(name="user_pwd", nullable=false)
    private String encryptedPwd;

    @Column(name="user_nickname", table = "tbl_user_info", nullable = false, unique = true)
    private String userNickname;

    @Column(name="user_tel", nullable = false, unique = true)
    private String userTel;                                                 //회원전번

    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail;                                               //회원이메일

    @Column(name = "user_gender", nullable = false)
    private char userGender;                                                //회원성별

    @Column(name = "user_birthday", nullable = false)
    private String userBirthday;                                            //회원생일

    @Column(name = "user_enroll_dt", nullable = false)
    private java.util.Date userEnrollDt;

    @Column(name = "user_role", table = "tbl_user_info", nullable = false)
    private String role;

}
