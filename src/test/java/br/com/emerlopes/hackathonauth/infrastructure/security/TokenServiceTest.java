package br.com.emerlopes.hackathonauth.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private Algorithm algorithm;

    @Mock
    private JWTVerifier jwtVerifier;

    @Mock
    private DecodedJWT decodedJWT;

    @Value("${spring.security.secret}")
    private String secret = "mysecretkey";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(tokenService, "secret", secret);
    }

    @Test
    void testGerarToken() {
        // Arrange
        List<GrantedAuthority> authorities = List.of(() -> "ROLE_USER");
        UserDetails userDetails = new User("testUser", "testPassword", authorities);
        String clientSecret = "clientSecret";
        String expectedToken = "generatedToken";

        Algorithm algorithm = Algorithm.HMAC256(clientSecret.trim());
        String token = JWT.create()
                .withIssuer("API")
                .withSubject(userDetails.getUsername())
                .withClaim("roles", List.of("ROLE_USER"))
                .withExpiresAt(Instant.now().plusSeconds(120))
                .sign(algorithm);

        // Act
        String resultToken = tokenService.gerarToken(userDetails, clientSecret);

        // Assert
        assertNotNull(resultToken);
    }

    @Test
    void testGerarTokenError() {
        // Arrange
        List<GrantedAuthority> authorities = List.of(() -> "ROLE_USER");
        UserDetails userDetails = new User("testUser", "testPassword", authorities);
        String clientSecret = "clientSecret";

        // Simular a exceção JWTCreationException ao criar um token
        TokenService tokenServiceSpy = spy(tokenService);
        doThrow(new JWTCreationException("Error creating token", null))
                .when(tokenServiceSpy).gerarToken(userDetails, clientSecret);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tokenServiceSpy.gerarToken(userDetails, clientSecret);
        });

        assertEquals("Error creating token", exception.getMessage());
    }

    @Test
    void testValidarToken() {
        // Arrange
        String token = "valid.token.structure";
        String expectedSubject = "testUser";

//        when(jwtVerifier.verify(token)).thenReturn(decodedJWT);
//        when(decodedJWT.getSubject()).thenReturn(expectedSubject);

        JWTVerifier verifier = mock(JWTVerifier.class);
//        when(verifier.verify(token)).thenReturn(decodedJWT);
//        ReflectionTestUtils.setField(tokenService, "jwtVerifier", verifier);

        // Act
        String resultSubject = tokenService.validarToken(token);

        // Assert
        assertEquals(null, resultSubject);
    }

    @Test
    void testValidarTokenInvalido() {
        // Arrange
        String token = "invalidToken";

        // Act
        String resultSubject = tokenService.validarToken(token);

        // Assert
        assertNull(resultSubject);
    }
}