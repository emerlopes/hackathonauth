package br.com.emerlopes.hackathonauth.application.exceptions;

public class CustomMissingServletRequestParameterException extends RuntimeException {
    public CustomMissingServletRequestParameterException(String parameterName) {
        super("O parâmetro de solicitação '" + parameterName + "' é obrigatório e não está presente.");
    }
}