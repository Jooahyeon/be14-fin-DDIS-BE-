package com.ddis.ddis_hr.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.*;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.*;

@SecurityScheme(
        name = "bearerAuth", // 꼭 이 이름으로! 컨트롤러의 @SecurityRequirement(name = "bearerAuth")와 일치
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("DDIS 프로젝트 API")
                        .version("v1")
                        .description("전자결재/근태/인사 관리 등 HR API 명세"));
    }
}