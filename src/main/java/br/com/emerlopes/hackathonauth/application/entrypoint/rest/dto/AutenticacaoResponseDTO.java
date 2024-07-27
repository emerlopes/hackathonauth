package br.com.emerlopes.hackathonauth.application.entrypoint.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AutenticacaoResponseDTO {
    @JsonProperty("token_acesso")
    private String tokenAcesso;
}
