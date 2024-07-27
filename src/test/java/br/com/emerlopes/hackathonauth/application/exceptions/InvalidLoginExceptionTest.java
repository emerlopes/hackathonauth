package br.com.emerlopes.hackathonauth.application.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.AuthenticationException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InvalidLoginExceptionTest {
    @Test
    void testExceptionMessage() {
        // Arrange
        String expectedMessage = "Usuário ou senha inválidos";

        // Act
        InvalidLoginException exception = new InvalidLoginException(expectedMessage);

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testExceptionInstance() {
        // Arrange
        String message = "Erro de autenticação";

        // Act
        InvalidLoginException exception = new InvalidLoginException(message);

        // Assert
        assertTrue(exception instanceof AuthenticationException);
    }
}