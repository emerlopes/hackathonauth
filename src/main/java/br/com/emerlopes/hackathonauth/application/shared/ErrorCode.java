package br.com.emerlopes.hackathonauth.application.shared;

import lombok.Getter;

@Getter
public enum ErrorCode {
    GENERIC_ERROR("GENERIC_ERROR", "An error occurred. Please try again later."),
    VALIDATION_ERROR("VALIDATION_ERROR", "Invalid input. Please check your data."),
    AUTHENTICATION_ERROR("AUTHENTICATION_ERROR", "Authentication failed. Please check your credentials."),
    AUTHORIZATION_ERROR("AUTHORIZATION_ERROR", "You do not have permission to perform this action."),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", "The requested resource was not found."),
    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "Is not possible to register a user with the same login.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}