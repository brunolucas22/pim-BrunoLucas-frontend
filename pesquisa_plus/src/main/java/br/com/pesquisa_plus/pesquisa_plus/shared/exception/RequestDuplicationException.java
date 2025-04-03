package br.com.pesquisa_plus.pesquisa_plus.shared.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RequestDuplicationException extends RuntimeException {
    public RequestDuplicationException(String message) {
        super(message);
    }
    public RequestDuplicationException(String message, Throwable cause) {
        super(message, cause);
    }
} 