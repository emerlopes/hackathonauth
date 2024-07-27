package br.com.emerlopes.hackathonauth.infrastructure.security;

import br.com.emerlopes.hackathonauth.infrastructure.database.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    @InjectMocks
    private AuthorizationService authorizationService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void testarCarregarUsuarioPorUsernameComSucesso() {
        // Arrange
        String username = "testUser";
        UserDetails expectedUserDetails = mock(UserDetails.class);

        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.of(expectedUserDetails));

        // Act
        UserDetails result = authorizationService.loadUserByUsername(username);

        // Assert
        assertEquals(expectedUserDetails, result);
        Mockito.verify(usuarioRepository).findByUsername(username);
    }

    @Test
    void testarCarregarUsuarioPorUsernameNaoEncontrado() {
        // Arrange
        String username = "nonExistentUser";

        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            authorizationService.loadUserByUsername(username);
        });

        assertEquals("User not found", exception.getMessage());
        Mockito.verify(usuarioRepository).findByUsername(username);
    }
}