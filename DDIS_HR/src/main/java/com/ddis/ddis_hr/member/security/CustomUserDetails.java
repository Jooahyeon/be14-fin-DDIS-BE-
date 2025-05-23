package com.ddis.ddis_hr.member.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;

@Getter
public class CustomUserDetails extends User {

    private final int userCode;  // 추가 필드 userCode

    public CustomUserDetails(String username, String password, int userCode,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userCode = userCode;
    }

    public int getUserCode() {
        return userCode;
    }
}
