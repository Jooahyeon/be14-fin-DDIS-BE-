package com.ddis.ddis_hr.member.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;

@Getter
public class CustomUserDetails extends User {

    private final int positionId;
    private final int rankId;
    private final int jobId;
    private final int headId;
    private final int departmentId;
    private final int teamId;

    public CustomUserDetails(String employeeId, String password, int positionId, int rankId, int jobId, int headId, int departmentId, int teamId, Collection<? extends GrantedAuthority> authorities) {
        super(employeeId, password, authorities);
        this.positionId = positionId;
        this.rankId = rankId;
        this.jobId = jobId;
        this.headId = headId;
        this.departmentId = departmentId;
        this.teamId = teamId;
    }

}
