package com.facilities.pet.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Pet Facilities API", version = "v0.0.1", description = "애완동물 시설 현황 API 명세서입니다."))
public class Swagger2Config {

    @Bean
    public GroupedOpenApi SecurityGroupOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("Security Open Api")
                .pathsToMatch("/api/**")
                .addOpenApiCustomiser(buildSecurityOpenApi())
                .build();
    }

    public OpenApiCustomiser buildSecurityOpenApi() {
        SecurityScheme securityScheme = new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .bearerFormat("JWT")
                .scheme("bearer");

        return OpenApi -> OpenApi
                .addSecurityItem(new SecurityRequirement().addList("jwt token"))
                .getComponents().addSecuritySchemes("jwt token", securityScheme);
    }
}