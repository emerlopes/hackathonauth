package br.com.emerlopes.hackathonauth.application.entrypoint.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponseDTO {
    @JsonProperty("token_valido")
    private boolean tokenValido;
}
