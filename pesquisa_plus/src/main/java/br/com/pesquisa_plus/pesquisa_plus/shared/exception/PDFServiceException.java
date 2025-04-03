package br.com.pesquisa_plus.pesquisa_plus.shared.exception;

public class PDFServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1807995281016301105L;

	public PDFServiceException(String message) {
		super(message);
	}

	public PDFServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
