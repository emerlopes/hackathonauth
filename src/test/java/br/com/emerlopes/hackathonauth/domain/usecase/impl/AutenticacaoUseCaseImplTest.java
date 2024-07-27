package br.com.emerlopes.hackathonauth.domain.usecase.impl;

import br.com.emerlopes.hackathonauth.domain.entity.AutenticacaoDomainEntity;
import br.com.emerlopes.hackathonauth.domain.repository.AutenticacaoDomainRepository;
import br.com.emerlopes.hackathonauth.domain.shared.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class AutenticacaoUseCaseImplTest {
    @InjectMocks
    private AutenticacaoUseCaseImpl autenticacaoUseCase;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AutenticacaoDomainRepository autenticacaoDomainRepository;


    @Test
    void testarExecucaoSucesso() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        AutenticacaoDomainEntity autenticacaoDomainEntity = AutenticacaoDomainEntity.builder()
                .username(username)
                .password(password)
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new User(username, password, authorities),
                password,
                authorities
        );

        Mockito.when(authenticationManager.authenticate(Mockito.any(Authentication.class))).thenReturn(authentication);
        Mockito.when(autenticacaoDomainRepository.gerarTokenAcesso(Mockito.any(AutenticacaoDomainEntity.class)))
                .thenReturn(autenticacaoDomainEntity);

        // Act
        AutenticacaoDomainEntity result = autenticacaoUseCase.execute(autenticacaoDomainEntity);

        // Assert
        assertEquals(username, result.getUsername());
        assertEquals(password, result.getPassword());
        assertEquals(1, result.getRoles().size());
        assertEquals(Role.USER, result.getRoles().get(0));

        // Capture and verify the repository interaction
        ArgumentCaptor<AutenticacaoDomainEntity> captor = ArgumentCaptor.forClass(AutenticacaoDomainEntity.class);
        Mockito.verify(autenticacaoDomainRepository).gerarTokenAcesso(captor.capture());
        assertEquals(username, captor.getValue().getUsername());
    }


    @Test
    void testarExecucaoFalha() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";

        AutenticacaoDomainEntity autenticacaoDomainEntity = AutenticacaoDomainEntity.builder()
                .username(username)
                .password(password)
                .build();

        Mockito.when(authenticationManager.authenticate(Mockito.any(Authentication.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        // Act and Assert
        try {
            autenticacaoUseCase.execute(autenticacaoDomainEntity);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertEquals("Invalid credentials", e.getMessage());
        }

        verifyNoInteractions(autenticacaoDomainRepository);
    }
}