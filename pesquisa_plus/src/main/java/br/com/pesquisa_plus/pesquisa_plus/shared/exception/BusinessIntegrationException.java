package br.com.pesquisa_plus.pesquisa_plus.shared.exception;

public class BusinessIntegrationException extends RuntimeException {

	 public BusinessIntegrationException(String message) {
	        super(message);
	    }
	    public BusinessIntegrationException(String message, Throwable cause) {
	        super(message, cause);
	    }
}
