package com.facilities.pet.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * . Swagger2Config
 */
@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Pet Facilities API", version = "v0.0.1", description = "애완동물 시설 현황 API 명세서입니다."
    )
)
public class Swagger2Config {

  /**
   * . SecurityGroupOpenApi
   */
  @Bean
  public GroupedOpenApi securityGroupOpenApi() {
    return GroupedOpenApi
        .builder()
        .group("Security Open Api")
        .pathsToMatch("/api/**")
        .addOpenApiCustomizer(buildSecurityOpenApi())
        .build();
  }

  /**
   * . buildSecurityOpenApi
   */
  public OpenApiCustomizer buildSecurityOpenApi() {
    SecurityScheme securityScheme = new SecurityScheme()
        .name("Authorization")
        .type(SecurityScheme.Type.HTTP)
        .in(SecurityScheme.In.HEADER)
        .bearerFormat("JWT")
        .scheme("bearer");

    return openAPI -> openAPI
        .addSecurityItem(new SecurityRequirement().addList("jwt token"))
        .getComponents().addSecuritySchemes("jwt token", securityScheme);
  }
}