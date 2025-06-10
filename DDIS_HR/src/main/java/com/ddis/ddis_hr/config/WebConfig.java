package com.ddis.ddis_hr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")                                // 全 경로
                .allowedOrigins("http://localhost:8080")         // Vue dev 서버
                .allowedMethods("GET","POST","PUT","DELETE", "PATCH", "OPTIONS")     // 허용 HTTP 메서드
                .allowedHeaders("*")                             // 모든 헤더 허용
                .allowCredentials(true);                         // 쿠키/인증 헤더 허용
    }

}
