package br.com.emerlopes.hackathonauth.application.exceptions;

import br.com.emerlopes.hackathonauth.application.shared.CustomResponseDTO;
import br.com.emerlopes.hackathonauth.application.shared.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private Logger logger;

    @Test
    public void testHandleFieldValidationException() throws NoSuchMethodException {
        BindingResult bindingResult = mock(BindingResult.class);

        MethodParameter methodParameter = new MethodParameter(
                GlobalExceptionHandlerTest.class.getDeclaredMethod("testHandleFieldValidationException"), -1);

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> responseEntity = globalExceptionHandler.handleFieldValidationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(ErrorCode.VALIDATION_ERROR.getCode(), responseEntity.getBody().errorCode());
        assertEquals(ErrorCode.VALIDATION_ERROR.getMessage(), responseEntity.getBody().message());
    }

    @Test
    public void testHandleInvalidLoginException() {

        InvalidLoginException ex = mock(InvalidLoginException.class);
        when(ex.getMessage()).thenReturn("Invalid login attempt");

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> responseEntity = globalExceptionHandler.handleInvalidLoginException(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals(ErrorCode.AUTHENTICATION_ERROR.getCode(), responseEntity.getBody().errorCode());
        assertEquals(ErrorCode.AUTHENTICATION_ERROR.getMessage(), responseEntity.getBody().message());
    }

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