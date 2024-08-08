package br.com.emerlopes.hackathonauth.application.entrypoint.rest.examples;

import br.com.emerlopes.hackathonauth.application.entrypoint.rest.dto.TokenResponseDTO;
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
        summary = "Validar Token de Autenticação",
        description = "API para validar um token de autenticação fornecido",
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Token validado com sucesso",
                        content = @Content(
                                schema = @Schema(implementation = TokenResponseDTO.class),
                                examples = @ExampleObject(
                                        name = "Token validado",
                                        summary = "Exemplo de resposta de token validado",
                                        value = """
                                                {
                                                    "token_valido": true
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
                                                    "message": "Invalid token",
                                                    "details": "/api/validate"
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
                                                    "details": "/api/validate"
                                                }
                                                """
                                )
                        )
                )
        }
)
public @interface ValidarTokenExample {
}
