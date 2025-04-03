package br.com.pesquisa_plus.pesquisa_plus.shared.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessGenericException extends RuntimeException {

	private static final long serialVersionUID = -3983096404724743460L;

	public BusinessGenericException(String message) {
        super(message);
    }
    
    public BusinessGenericException(String message, Throwable cause) {
        super(message, cause);
    }
    
} 