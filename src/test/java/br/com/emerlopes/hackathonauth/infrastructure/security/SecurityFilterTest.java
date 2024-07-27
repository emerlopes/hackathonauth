package br.com.emerlopes.hackathonauth.infrastructure.security;

import br.com.emerlopes.hackathonauth.infrastructure.database.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityFilterTest {

    @InjectMocks
    private SecurityFilter securityFilter;

    @Mock
    private TokenService tokenService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterInternalValidToken() throws ServletException, IOException {
        // Arrange
        String token = "Bearer validToken";
        String username = "testUser";
        UserDetails userDetails = mock(UserDetails.class);

        when(request.getHeader("Authorization")).thenReturn(token);
        when(tokenService.validateToken("validToken")).thenReturn(username);
        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.of(userDetails));

        // Act
        securityFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(usuarioRepository).findByUsername(username);
        verify(filterChain).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternalInvalidToken() throws ServletException, IOException {
        // Arrange
        String token = "Bearer invalidToken";

        when(request.getHeader("Authorization")).thenReturn(token);
        when(tokenService.validateToken("invalidToken")).thenReturn(null);

        // Act
        securityFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(usuarioRepository, never()).findByUsername(anyString());
        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternalUserNotFound() throws ServletException, IOException {
        // Arrange
        String token = "Bearer validToken";
        String username = "testUser";

        when(request.getHeader("Authorization")).thenReturn(token);
        when(tokenService.validateToken("validToken")).thenReturn(username);
        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        securityFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(usuarioRepository).findByUsername(username);
        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternalNoToken() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        securityFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(usuarioRepository, never()).findByUsername(anyString());
        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}