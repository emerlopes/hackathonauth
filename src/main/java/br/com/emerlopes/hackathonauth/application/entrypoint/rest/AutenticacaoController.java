package br.com.emerlopes.hackathonauth.application.entrypoint.rest;

import br.com.emerlopes.hackathonauth.application.entrypoint.rest.dto.AutenticacaoResponseDTO;
import br.com.emerlopes.hackathonauth.application.entrypoint.rest.dto.TokenResponseDTO;
import br.com.emerlopes.hackathonauth.application.entrypoint.rest.examples.GerarTokenExample;
import br.com.emerlopes.hackathonauth.application.entrypoint.rest.examples.ValidarTokenExample;
import br.com.emerlopes.hackathonauth.application.exceptions.InvalidLoginException;
import br.com.emerlopes.hackathonauth.domain.entity.AutenticacaoDomainEntity;
import br.com.emerlopes.hackathonauth.domain.usecase.AutenticacaoUseCase;
import br.com.emerlopes.hackathonauth.domain.usecase.ValidarTokenUseCase;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(AutenticacaoController.class);
    private final AutenticacaoUseCase autenticacaoUseCase;
    private final ValidarTokenUseCase validarTokenUseCase;

    public AutenticacaoController(
            final AutenticacaoUseCase autenticacaoUseCase,
            final ValidarTokenUseCase validarTokenUseCase
    ) {
        this.autenticacaoUseCase = autenticacaoUseCase;
        this.validarTokenUseCase = validarTokenUseCase;
    }

    @GerarTokenExample
    @PostMapping("/token")
    public ResponseEntity<AutenticacaoResponseDTO> getToken(
            final @RequestParam String username,
            final @RequestParam String password
//            final @RequestParam("client_secret") String clientSecret
    ) throws InvalidLoginException {
        try {
            logger.info("Gerando token para usuario: {}", username);

            AutenticacaoDomainEntity autenticacaoDomainEntity = AutenticacaoDomainEntity.builder()
                    .username(username)
                    .password(password)
                    .secret("d6b93fc8-0ada-458e-b264-bcd152d07511")
                    .build();

            AutenticacaoDomainEntity resultadoExecucaoUseCase = autenticacaoUseCase.execute(autenticacaoDomainEntity);

            logger.info("Token gerado para o usuario: {}", username);

            AutenticacaoResponseDTO autenticacaoResponseDTO = AutenticacaoResponseDTO.builder()
                    .tokenAcesso(resultadoExecucaoUseCase.getToken())
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(autenticacaoResponseDTO);

        } catch (final Exception exception) {
            logger.error("Erro ao gerar token para usuario: {}", username, exception);
            throw new InvalidLoginException("Erro ao gerar token");
        }
    }


    @ValidarTokenExample
    @PostMapping("/validate")
    public ResponseEntity<TokenResponseDTO> validateToken(
            @RequestHeader("Authorization") String accessToken
    ) {

        logger.info("Validando token");

        AutenticacaoDomainEntity autenticacaoDomainEntity = AutenticacaoDomainEntity.builder()
                .token(accessToken)
                .build();

        AutenticacaoDomainEntity tokenValidationResult = validarTokenUseCase.execute(autenticacaoDomainEntity);

        logger.info("Token validado");

        TokenResponseDTO tokenResponse = TokenResponseDTO.builder()
                .tokenValido(tokenValidationResult.isValid())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(tokenResponse);
    }

}
