package br.com.pesquisa_plus.pesquisa_plus.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class TokenExpiredRequestException extends RuntimeException {

    private final String tokenType;
    private final String token;
    private final String message;

    public TokenExpiredRequestException(String tokenType, String token, String message) {
        super(String.format("Invalid [%s] token [%s] : %s", token, tokenType, message));
        this.tokenType = tokenType;
        this.token = token;
        this.message = message;
    }
}
