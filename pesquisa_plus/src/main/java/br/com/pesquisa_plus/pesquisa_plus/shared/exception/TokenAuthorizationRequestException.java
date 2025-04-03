package br.com.pesquisa_plus.pesquisa_plus.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class TokenAuthorizationRequestException extends RuntimeException {

    private final String resource;
    private final String message;

    public TokenAuthorizationRequestException(String resource, String message) {
        super(String.format("[%s] : %s", resource,  message));
        this.resource = resource;
        this.message = message;
    }
}
