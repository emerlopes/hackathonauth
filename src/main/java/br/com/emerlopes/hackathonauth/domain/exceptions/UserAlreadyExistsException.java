package br.com.emerlopes.hackathonauth.domain.exceptions;

import lombok.Getter;

import java.io.Serial;

@Getter
public class UserAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String errorCode;

    public UserAlreadyExistsException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
