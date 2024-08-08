package br.com.emerlopes.hackathonauth.application.entrypoint.rest;

import br.com.emerlopes.hackathonauth.application.entrypoint.rest.dto.AutenticacaoResponseDTO;
import br.com.emerlopes.hackathonauth.application.entrypoint.rest.dto.TokenResponseDTO;
import br.com.emerlopes.hackathonauth.application.exceptions.InvalidLoginException;
import br.com.emerlopes.hackathonauth.application.shared.CustomResponseDTO;
import br.com.emerlopes.hackathonauth.domain.entity.AutenticacaoDomainEntity;
import br.com.emerlopes.hackathonauth.domain.usecase.AutenticacaoUseCase;
import br.com.emerlopes.hackathonauth.domain.usecase.ValidarTokenUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class AutenticacaoControllerTest {

    @InjectMocks
    private AutenticacaoController autenticacaoController;

    @Mock
    private Logger logger;

    @Mock
    private AutenticacaoUseCase autenticacaoUseCase;

    @Mock
    private ValidarTokenUseCase validarTokenUseCase;

    @Test
    void testarGeracaoTokenSucesso() throws InvalidLoginException {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        String clientSecret = UUID.randomUUID().toString();
        String generatedToken = "generatedToken";

        AutenticacaoDomainEntity resultadoExecucaoUseCase = AutenticacaoDomainEntity.builder()
                .username(username)
                .token(generatedToken)
                .build();

        Mockito.when(autenticacaoUseCase.execute(Mockito.any(AutenticacaoDomainEntity.class))).thenReturn(resultadoExecucaoUseCase);

        // Act
        ResponseEntity<AutenticacaoResponseDTO> responseEntity = autenticacaoController.getToken(username, password);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(generatedToken, responseEntity.getBody().getTokenAcesso());
    }

    @Test
    void testarGeracaoTokenFalha() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        String clientSecret = UUID.randomUUID().toString();

        // Simulando uma excecao de tempo de execução
        doThrow(new RuntimeException("Erro ao gerar token")).when(autenticacaoUseCase).execute(Mockito.any(AutenticacaoDomainEntity.class));

        // Act and Assert
        try {
            autenticacaoController.getToken(username, password);
        } catch (InvalidLoginException e) {
            assertEquals("Erro ao gerar token", e.getMessage());
        }
    }

    @Test
    void testarValidacaoTokenSucesso() {
        // Arrange
        String accessToken = "validAccessToken";

        AutenticacaoDomainEntity tokenValidationResult = AutenticacaoDomainEntity.builder()
                .valid(true)
                .build();

        Mockito.when(validarTokenUseCase.execute(Mockito.any(AutenticacaoDomainEntity.class))).thenReturn(tokenValidationResult);

        // Act
        ResponseEntity<TokenResponseDTO> responseEntity = autenticacaoController.validateToken(accessToken);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).isTokenValido());
    }

    @Test
    void testarValidacaoTokenInvalido() {
        // Arrange
        String accessToken = "invalidAccessToken";

        AutenticacaoDomainEntity tokenValidationResult = AutenticacaoDomainEntity.builder()
                .valid(false)
                .build();

        Mockito.when(validarTokenUseCase.execute(Mockito.any(AutenticacaoDomainEntity.class))).thenReturn(tokenValidationResult);

        // Act
        ResponseEntity<TokenResponseDTO> responseEntity = autenticacaoController.validateToken(accessToken);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(Objects.requireNonNull(responseEntity.getBody()).isTokenValido());
    }

}