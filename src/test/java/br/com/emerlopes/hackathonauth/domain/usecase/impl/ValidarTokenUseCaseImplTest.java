package br.com.emerlopes.hackathonauth.domain.usecase.impl;

import br.com.emerlopes.hackathonauth.domain.entity.AutenticacaoDomainEntity;
import br.com.emerlopes.hackathonauth.domain.repository.AutenticacaoDomainRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidarTokenUseCaseImplTest {

    @InjectMocks
    private ValidarTokenUseCaseImpl validarTokenUseCase;

    @Mock
    private AutenticacaoDomainRepository autenticacaoDomainRepository;

    @Test
    void testarExecucaoSucesso() {
        // Arrange
        String token = "validToken";

        AutenticacaoDomainEntity autenticacaoDomainEntity = AutenticacaoDomainEntity.builder()
                .token(token)
                .build();

        AutenticacaoDomainEntity expectedEntity = AutenticacaoDomainEntity.builder()
                .token(token)
                .valid(true)
                .build();

        Mockito.when(autenticacaoDomainRepository.validarTokenAcesso(Mockito.any(AutenticacaoDomainEntity.class)))
                .thenReturn(expectedEntity);

        // Act
        AutenticacaoDomainEntity result = validarTokenUseCase.execute(autenticacaoDomainEntity);

        // Assert
        assertEquals(expectedEntity.isValid(), result.isValid());
        assertEquals(expectedEntity.getToken(), result.getToken());
    }

    @Test
    void testarExecucaoFalha() {
        // Arrange
        String token = "invalidToken";

        AutenticacaoDomainEntity autenticacaoDomainEntity = AutenticacaoDomainEntity.builder()
                .token(token)
                .build();

        AutenticacaoDomainEntity expectedEntity = AutenticacaoDomainEntity.builder()
                .token(token)
                .valid(false)
                .build();

        Mockito.when(autenticacaoDomainRepository.validarTokenAcesso(Mockito.any(AutenticacaoDomainEntity.class)))
                .thenReturn(expectedEntity);

        // Act
        AutenticacaoDomainEntity result = validarTokenUseCase.execute(autenticacaoDomainEntity);

        // Assert
        assertEquals(expectedEntity.isValid(), result.isValid());
        assertEquals(expectedEntity.getToken(), result.getToken());
    }
}