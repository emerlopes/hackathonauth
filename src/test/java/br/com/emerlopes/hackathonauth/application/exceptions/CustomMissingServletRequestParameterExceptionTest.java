package br.com.emerlopes.hackathonauth.application.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomMissingServletRequestParameterExceptionTest {
    @Test
    void testExceptionMessage() {
        // Arrange
        String parameterName = "username";
        String expectedMessage = "O parâmetro de solicitação '" + parameterName + "' é obrigatório e não está presente.";

        // Act
        CustomMissingServletRequestParameterException exception =
                new CustomMissingServletRequestParameterException(parameterName);

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testNullParameterName() {
        // Arrange
        String parameterName = null;
        String expectedMessage = "O parâmetro de solicitação 'null' é obrigatório e não está presente.";

        // Act
        CustomMissingServletRequestParameterException exception =
                new CustomMissingServletRequestParameterException(parameterName);

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testEmptyParameterName() {
        // Arrange
        String parameterName = "";
        String expectedMessage = "O parâmetro de solicitação '' é obrigatório e não está presente.";

        // Act
        CustomMissingServletRequestParameterException exception =
                new CustomMissingServletRequestParameterException(parameterName);

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }
}