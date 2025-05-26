package com.ddis.ddis_hr.member.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;

@Getter
public class CustomUserDetails extends User {

    private final Long positionId;
    private final Long rankId;
    private final Long jobId;
    private final Long headId;
    private final Long departmentId;
    private final Long teamId;

    public CustomUserDetails(String employeeId, String password, Long positionId, Long rankId, Long jobId, Long headId, Long departmentId, Long teamId, Collection<? extends GrantedAuthority> authorities) {
        super(employeeId, password, authorities);
        this.positionId = positionId;
        this.rankId = rankId;
        this.jobId = jobId;
        this.headId = headId;
        this.departmentId = departmentId;
        this.teamId = teamId;
    }

}
