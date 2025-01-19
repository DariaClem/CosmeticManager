package com.cosmetic_manager.cosmetic_manager.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI defineOpenAPI() {
        Server server = new Server();

        server.setUrl("http://localhost:8080");
        server.setDescription("Local server");

        Info info = new Info()
                .title("Cosmetic Manager API")
                .description("CosmeticManager is a Spring Boot 3.4 application for tracking beauty product usage, aiming to support sustainability in daily life. It also encourages users to explore new styles with their cosmetic products, creating an inspiring atmosphere for them.")
                .version("1.0.0");

        return new OpenAPI().info(info).servers(List.of(server));
    }
}
