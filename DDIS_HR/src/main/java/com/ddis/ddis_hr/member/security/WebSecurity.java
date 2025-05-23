package com.ddis.ddis_hr.member.security;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collections;

@Configuration
public class WebSecurity {

    private JwtAuthenticationProvider jwtAuthenticationProvider;
    private Environment env;                //의존성 주입 DI
    private JwtUtil jwtUtil;

    @Autowired
    public WebSecurity(JwtAuthenticationProvider jwtAuthenticationProvider,Environment env,JwtUtil jwtUtil) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.env = env;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(jwtAuthenticationProvider));

    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
        http.csrf((csrf) -> csrf.disable());

        http.authorizeHttpRequests(authz ->
                authz.requestMatchers(new AntPathRequestMatcher("/health", "GET")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/users/**", "POST")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/members/**", "GET")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/email-verification/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
                        .anyRequest().authenticated()
        )
                .authenticationManager(authenticationManager())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilter(getAuthenticationFilter(authenticationManager()));

        http.addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    private Filter getAuthenticationFilter(AuthenticationManager authenticationManager){
        return new AuthenticationFilter(authenticationManager, env);
    }
}
