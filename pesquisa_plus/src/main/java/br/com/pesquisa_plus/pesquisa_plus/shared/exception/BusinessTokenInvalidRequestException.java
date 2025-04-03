package br.com.pesquisa_plus.pesquisa_plus.shared.exception;

public class BusinessTokenInvalidRequestException extends RuntimeException {

	 public BusinessTokenInvalidRequestException(String message) {
	        super(message);
	    }
	    public BusinessTokenInvalidRequestException(String message, Throwable cause) {
	        super(message, cause);
	    }
}
