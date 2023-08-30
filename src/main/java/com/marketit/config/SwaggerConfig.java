package com.marketit.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api() {
        String[] paths = {"/**"};
        return GroupedOpenApi.builder()
                .group("marketit 과제")
                .pathsToMatch(paths)
                .build();
    }
}
