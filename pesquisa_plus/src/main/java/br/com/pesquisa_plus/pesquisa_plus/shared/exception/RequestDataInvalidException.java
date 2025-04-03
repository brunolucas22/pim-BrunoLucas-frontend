package br.com.pesquisa_plus.pesquisa_plus.shared.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RequestDataInvalidException extends RuntimeException {
    public RequestDataInvalidException(String message) {
        super(message);
    }
    public RequestDataInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
} 