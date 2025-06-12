package com.ddis.ddis_hr.member.security;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurity {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final Environment env;
    private final JwtUtil jwtUtil;

    @Autowired
    public WebSecurity(JwtAuthenticationProvider jwtAuthenticationProvider,
                       Environment env,
                       JwtUtil jwtUtil) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.env = env;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(jwtAuthenticationProvider));
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                // 1) CSRF 비활성화
                .csrf(csrf -> csrf.disable())
                // 2) Security 레벨에서 CORS 활성화 (람다 스타일, deprecated-free)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http
                .authorizeHttpRequests(authz ->
                        authz
                                .requestMatchers(new AntPathRequestMatcher("/health", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/users/**", "POST")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/members/**", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/email-verification/**")).permitAll()
                                // 모든 경로 허용 → 필요시 조정
                                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
//                                .requestMatchers(new AntPathRequestMatcher("/payroll/employees/**")).hasAnyRole("HR")
                                .anyRequest().authenticated()
                )
                // 3) stateless 세션 정책
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 4) AuthenticationManager 설정
                .authenticationManager(authenticationManager());

        // 5) 커스텀 인증 필터 등록
        http.addFilter(getAuthenticationFilter(authenticationManager()));
        // 6) JWT 검증 필터 등록
        http.addFilterBefore(new JwtFilter(jwtUtil),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private Filter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new AuthenticationFilter(authenticationManager, env);
    }

    /**
     * CORS 정책 정의 빈
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // 프론트 개발 서버 도메인 (Vue 기본 5173 포트)
        config.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
        // 인증 쿠키/헤더 허용
        config.setAllowCredentials(true);
        // 허용 HTTP 메서드
        config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS", "PATCH"));
        // 허용 요청 헤더
        config.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));
        // 클라이언트에서 접근을 허용할 응답 헤더
        config.setExposedHeaders(Arrays.asList("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 모든 경로에 대해 위 설정 적용
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
