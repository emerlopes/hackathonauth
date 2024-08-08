package br.com.emerlopes.hackathonauth.infrastructure.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "hackathonauth", version = "v1"),
        servers = {
                @Server(url = "http://localhost:8080", description = "Servidor Local"),
                @Server(url = "http://hackathonauth.com", description = "Servidor Docker")
        }
)
public class SwaggerConfig {
}