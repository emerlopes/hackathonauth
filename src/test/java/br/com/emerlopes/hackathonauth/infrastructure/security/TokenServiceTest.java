package br.com.emerlopes.hackathonauth.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
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

    @Mock
    private JWT jwt;

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

    @Test
    public void testValidateToken_ValidToken() {
        String token = "validToken";
        String expectedSubject = "user123";

        try (MockedStatic<JWT> mockedJWT = mockStatic(JWT.class)) {
            JWTVerifier verifier = mock(JWTVerifier.class);
            DecodedJWT decodedJWT = mock(DecodedJWT.class);
            Verification verification = mock(Verification.class);

            mockedJWT.when(() -> JWT.require(any(Algorithm.class))).thenReturn(verification);
            when(verification.withIssuer(anyString())).thenReturn(verification);
            when(verification.build()).thenReturn(verifier);
            when(verifier.verify(token)).thenReturn(decodedJWT);
            when(decodedJWT.getSubject()).thenReturn(expectedSubject);

            String subject = tokenService.validateToken(token);

            assertEquals(expectedSubject, subject);
        }
    }

    @Test
    public void testValidateToken_InvalidToken() {
        String token = "invalidToken";

        try (MockedStatic<JWT> mockedJWT = mockStatic(JWT.class)) {
            JWTVerifier verifier = mock(JWTVerifier.class);
            Verification verification = mock(Verification.class);

            mockedJWT.when(() -> JWT.require(any(Algorithm.class))).thenReturn(verification);
            when(verification.withIssuer(anyString())).thenReturn(verification);
            when(verification.build()).thenReturn(verifier);
            when(verifier.verify(token)).thenThrow(JWTVerificationException.class);

            DecodedJWT decodedJWT = mock(DecodedJWT.class);
            when(JWT.decode(token)).thenReturn(decodedJWT);
            when(decodedJWT.getSubject()).thenReturn(null);

            String subject = tokenService.validateToken(token);

            assertNull(subject);
        }
    }

    @Test
    public void testValidateToken_MalformedToken() {
        String token = "malformedToken";

        try (MockedStatic<JWT> mockedJWT = mockStatic(JWT.class)) {
            JWTVerifier verifier = mock(JWTVerifier.class);
            Verification verification = mock(Verification.class);

            mockedJWT.when(() -> JWT.require(any(Algorithm.class))).thenReturn(verification);
            when(verification.withIssuer(anyString())).thenReturn(verification);
            when(verification.build()).thenReturn(verifier);
            when(verifier.verify(token)).thenThrow(JWTVerificationException.class);

            when(JWT.decode(token)).thenThrow(RuntimeException.class);

            String subject = tokenService.validateToken(token);

            assertNull(subject);
        }
    }
}