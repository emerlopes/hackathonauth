package br.com.emerlopes.hackathonauth.application.exceptions;

import javax.naming.AuthenticationException;

public class InvalidLoginException extends AuthenticationException {

    public InvalidLoginException(
            final String message
    ) {
        super(message);
    }
}