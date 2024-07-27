package br.com.emerlopes.hackathonauth.application.exceptions;

import br.com.emerlopes.hackathonauth.application.shared.CustomResponseDTO;
import br.com.emerlopes.hackathonauth.application.shared.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private Logger logger;

    @Test
    void testHandleMissingServletRequestParameterException() {
        // Arrange
        MissingServletRequestParameterException ex = new MissingServletRequestParameterException("username", "String");

        // Act
        ResponseEntity<CustomResponseDTO<String>> responseEntity = globalExceptionHandler.handleMissingServletRequestParameterException(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("O parâmetro de solicitação 'username' é obrigatório e não está presente.", responseEntity.getBody().getData());
    }
}