package br.com.emerlopes.hackathonauth.application.entrypoint.rest.examples;

import br.com.emerlopes.hackathonauth.application.entrypoint.rest.dto.AutenticacaoResponseDTO;
import br.com.emerlopes.hackathonauth.application.shared.CustomErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Operation(
        summary = "Gerar Token de Autenticação",
        description = "API para gerar um token de autenticação com base nas credenciais fornecidas",
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Token gerado com sucesso",
                        content = @Content(
                                schema = @Schema(implementation = AutenticacaoResponseDTO.class),
                                examples = @ExampleObject(
                                        name = "Token gerado",
                                        summary = "Exemplo de resposta de token gerado",
                                        value = """
                                                {
                                                    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                                                }
                                                """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = @Content(
                                schema = @Schema(implementation = CustomErrorResponse.class),
                                examples = @ExampleObject(
                                        name = "Resposta não autorizada",
                                        summary = "Exemplo de resposta de erro de autorização",
                                        value = """
                                                {
                                                    "timestamp": "2023-08-07T14:00:00",
                                                    "message": "Invalid credentials",
                                                    "details": "/api/token"
                                                }
                                                """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content = @Content(
                                schema = @Schema(implementation = CustomErrorResponse.class),
                                examples = @ExampleObject(
                                        name = "Resposta de erro interno do servidor",
                                        summary = "Exemplo de resposta de erro interno do servidor",
                                        value = """
                                                {
                                                    "timestamp": "2023-08-07T14:00:00",
                                                    "message": "An unexpected error occurred",
                                                    "details": "/api/token"
                                                }
                                                """
                                )
                        )
                )
        }
)
public @interface GerarTokenExample {
}
