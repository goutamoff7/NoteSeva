package com.noteseva.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customSwaggerConfig() {
        OpenAPI openAPI = new OpenAPI();
        return openAPI
                .info(
                        new Info()
                                .title("NoteSeva APIs")
                                .description("By Team Smashers")
                )
                .servers(
                        List.of(
                                new Server().url("http://localhost:8080").description("local"),
                                new Server().url("http://localhost:8081").description("live"))
                )
                .tags(Arrays.asList(
                                new Tag().name("Admin APIs"),
                                new Tag().name("Public APIs"),
                                new Tag().name("HealthCheck API"),
                                new Tag().name("Notes APIs"),
                                new Tag().name("Organizer APIs"),
                                new Tag().name("Previous Year Question APIs"),
                                new Tag().name("SubjectAssignment APIs"),
                                new Tag().name("Oauth2.0 Login API"),
                                new Tag().name("User APIs"),
                                new Tag().name("AppCache API"),
                                new Tag().name("Bookmark APIs")

                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes(
                        "bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                ));
    }
}
