package br.com.emerlopes.hackathonauth.application.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ErrorCodeTest {

    @Test
    void testGenericError() {
        // Act & Assert
        assertEquals("GENERIC_ERROR", ErrorCode.GENERIC_ERROR.getCode());
        assertEquals("An error occurred. Please try again later.", ErrorCode.GENERIC_ERROR.getMessage());
    }

    @Test
    void testValidationError() {
        // Act & Assert
        assertEquals("VALIDATION_ERROR", ErrorCode.VALIDATION_ERROR.getCode());
        assertEquals("Invalid input. Please check your data.", ErrorCode.VALIDATION_ERROR.getMessage());
    }

    @Test
    void testAuthenticationError() {
        // Act & Assert
        assertEquals("AUTHENTICATION_ERROR", ErrorCode.AUTHENTICATION_ERROR.getCode());
        assertEquals("Authentication failed. Please check your credentials.", ErrorCode.AUTHENTICATION_ERROR.getMessage());
    }

    @Test
    void testAuthorizationError() {
        // Act & Assert
        assertEquals("AUTHORIZATION_ERROR", ErrorCode.AUTHORIZATION_ERROR.getCode());
        assertEquals("You do not have permission to perform this action.", ErrorCode.AUTHORIZATION_ERROR.getMessage());
    }

    @Test
    void testResourceNotFound() {
        // Act & Assert
        assertEquals("RESOURCE_NOT_FOUND", ErrorCode.RESOURCE_NOT_FOUND.getCode());
        assertEquals("The requested resource was not found.", ErrorCode.RESOURCE_NOT_FOUND.getMessage());
    }

    @Test
    void testUserAlreadyExists() {
        // Act & Assert
        assertEquals("USER_ALREADY_EXISTS", ErrorCode.USER_ALREADY_EXISTS.getCode());
        assertEquals("Is not possible to register a user with the same login.", ErrorCode.USER_ALREADY_EXISTS.getMessage());
    }
}