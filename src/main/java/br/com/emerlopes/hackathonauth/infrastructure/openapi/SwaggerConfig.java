package br.com.emerlopes.hackathonauth.infrastructure.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "hackathonauth", version = "v1"),
        servers = {
                @Server(url = "http://localhost:8081", description = "Servidor Local"),
                @Server(url = "http://hackathonauth:8081", description = "Servidor Docker"),
                @Server(url = "http://hackathonauth:8080", description = "Servidor Docker")
        }
)
public class SwaggerConfig {
}