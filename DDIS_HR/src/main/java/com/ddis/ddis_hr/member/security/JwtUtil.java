package com.ddis.ddis_hr.member.security;

import com.ddis.ddis_hr.member.command.application.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil {

    private final Key key;
    private final UserService userService;

    @Autowired
    public JwtUtil(
            @Value("${token.secret}") String secretKey,
            UserService userService
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userService = userService;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty");
        }

        return false;
    }

    public Authentication getAuthentication(String token) {

        Claims claims = parseClaims(token);

        UserDetails userDetails = userService.loadUserByUsername(claims.getSubject());

        Collection<GrantedAuthority> authorities = null;
        if(claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다");
        } else {
            authorities = Arrays.stream(claims.get("auth").toString()
                            .replace("[","")
                            .replace("]", "")
                            .split(", "))
                    .map(role-> new SimpleGrantedAuthority(role))
                    .collect(Collectors.toList());
        }

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getEmpIdFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return parseClaims(token).getSubject();
    }
    public String createToken(CustomUserDetails userDetails, long expirationMillis) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("auth", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        claims.put("teamId", userDetails.getTeamId());
        claims.put("positionId", userDetails.getPositionId());
        claims.put("rankId", userDetails.getRankId());
        claims.put("jobId", userDetails.getJobId());
        claims.put("headId", userDetails.getHeadId());
        claims.put("departmentId", userDetails.getDepartmentId());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

}

