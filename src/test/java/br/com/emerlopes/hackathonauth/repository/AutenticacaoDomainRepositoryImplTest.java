package br.com.emerlopes.hackathonauth.repository;

import br.com.emerlopes.hackathonauth.domain.entity.AutenticacaoDomainEntity;
import br.com.emerlopes.hackathonauth.domain.shared.Role;
import br.com.emerlopes.hackathonauth.infrastructure.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AutenticacaoDomainRepositoryImplTest {
    @InjectMocks
    private AutenticacaoDomainRepositoryImpl autenticacaoDomainRepository;

    @Mock
    private TokenService tokenService;

    @Test
    void testGerarTokenAcesso() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        String secret = "testSecret";
        String expectedToken = "generatedToken";
        List<Role> roles = List.of(Role.USER);

        AutenticacaoDomainEntity autenticacaoDomainEntity = AutenticacaoDomainEntity.builder()
                .username(username)
                .password(password)
                .roles(roles)
                .secret(secret)
                .build();

        UserDetails userDetails = User.withUsername(username)
                .password(password)
                .authorities(roles.stream().map(Role::toString).toArray(String[]::new))
                .build();

        Mockito.when(tokenService.gerarToken(Mockito.any(User.class), Mockito.any(String.class))).thenReturn(expectedToken);

        // Act
        AutenticacaoDomainEntity result = autenticacaoDomainRepository.gerarTokenAcesso(autenticacaoDomainEntity);

        // Assert
        assertEquals(username, result.getUsername());
        assertEquals(expectedToken, result.getToken());
        Mockito.verify(tokenService).gerarToken(userDetails, secret);
    }

    @Test
    void testValidarTokenAcesso() {
        // Arrange
        String token = "testToken";

        AutenticacaoDomainEntity autenticacaoDomainEntity = AutenticacaoDomainEntity.builder()
                .token(token)
                .build();

        Mockito.when(tokenService.validarToken(token)).thenReturn(token);

        // Act
        AutenticacaoDomainEntity result = autenticacaoDomainRepository.validarTokenAcesso(autenticacaoDomainEntity);

        // Assert
        assertTrue(result.isValid());
        Mockito.verify(tokenService).validarToken(token);
    }

    @Test
    void testValidarTokenAcessoInvalido() {
        // Arrange
        String token = "testToken";

        AutenticacaoDomainEntity autenticacaoDomainEntity = AutenticacaoDomainEntity.builder()
                .token(token)
                .build();

        Mockito.when(tokenService.validarToken(token)).thenReturn(null);

        // Act
        AutenticacaoDomainEntity result = autenticacaoDomainRepository.validarTokenAcesso(autenticacaoDomainEntity);

        // Assert
        assertFalse(result.isValid());
        Mockito.verify(tokenService).validarToken(token);
    }
}