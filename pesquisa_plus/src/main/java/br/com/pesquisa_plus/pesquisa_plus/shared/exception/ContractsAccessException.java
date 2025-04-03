package br.com.pesquisa_plus.pesquisa_plus.shared.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ContractsAccessException extends RuntimeException {

	private static final long serialVersionUID = -3342997205643133146L;

	public ContractsAccessException(String message) {
        super(message);
    }
    
    public ContractsAccessException(String message, Throwable cause) {
        super(message, cause);
    }
    
} 