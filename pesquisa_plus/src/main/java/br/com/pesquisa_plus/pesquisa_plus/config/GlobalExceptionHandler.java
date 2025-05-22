package br.com.pesquisa_plus.pesquisa_plus.config;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mapping.MappingException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.AntPathMatcher;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;



import br.com.pesquisa_plus.pesquisa_plus.shared.dto.ResponseErrorDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.ResponseErrorValidatorDTO;
// import br.com.pesquisa_plus.pesquisa_plus.shared.exception.ApiDataInvalidException;
// import br.com.pesquisa_plus.pesquisa_plus.shared.exception.BusinessGenericException;
// import br.com.pesquisa_plus.pesquisa_plus.shared.exception.BusinessIntegrationException;
// import br.com.pesquisa_plus.pesquisa_plus.shared.exception.BusinessTokenInvalidRequestException;
// import br.com.pesquisa_plus.pesquisa_plus.shared.exception.ContractsAccessException;
// import br.com.pesquisa_plus.pesquisa_plus.shared.exception.RequestDataInvalidException;
// import br.com.pesquisa_plus.pesquisa_plus.shared.exception.RequestDuplicationException;
// import br.com.pesquisa_plus.pesquisa_plus.shared.exception.SessionExpiredException;
// import br.com.pesquisa_plus.pesquisa_plus.shared.exception.TokenAuthorizationRequestException;
// import br.com.pesquisa_plus.pesquisa_plus.shared.exception.TokenExpiredRequestException;
// import br.com.pesquisa_plus.pesquisa_plus.shared.exception.TokenInvalidRequestException;
import jakarta.persistence.EntityNotFoundException;


@RestControllerAdvice
public class GlobalExceptionHandler {

	private String dafaultMsg = "Operação não realizada! Entre em contato com o administrador do sistema!";
	private String dafaultMsg2 = "Estamos passando por uma instabilidade nos nossos sistemas! Aguarde alguns instantes e tente a operação novamente.";
	private String msgErrorPKI = "No momento não é possível assinar documentos, aguarde alguns instantes e tente a operação novamente.";

	
	private AntPathMatcher pathMatcher = new AntPathMatcher();
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseErrorDTO> handleValidationErrors(MethodArgumentNotValidException ex) {
		ex.printStackTrace();
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
				.collect(Collectors.toList()); 
		return new ResponseEntity<ResponseErrorDTO>(getErrorDTO(ex.getMessage(), ex.getClass().getSimpleName(),errors.get(0), HttpStatus.BAD_REQUEST), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	// @ExceptionHandler(RequestDataInvalidException.class)
	// public ResponseEntity<ResponseErrorDTO> handleRequestDataInvalidErrors(RequestDataInvalidException ex) {
	// 	ex.printStackTrace();
	// 	String msg = ex.getMessage()!= null ? ex.getMessage() : dafaultMsg;
	// 	return new ResponseEntity<ResponseErrorDTO>(getErrorDTO(msg, ex.getClass().getSimpleName(), ex.getMessage(), HttpStatus.BAD_REQUEST), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	// }

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ResponseErrorDTO> handleNotFoundException(EntityNotFoundException ex) {
		ex.printStackTrace();
		return new ResponseEntity<ResponseErrorDTO>(getErrorDTO(dafaultMsg, ex.getClass().getSimpleName(), ex.getMessage(), HttpStatus.NOT_FOUND), new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public final ResponseEntity<ResponseErrorDTO> handleDataIntegrityViolationExceptions(Exception ex) {
		String datail = new String();
		ex.printStackTrace();
		if (ex instanceof DuplicateKeyException)
			datail = "Exception: " + ex.toString();

		if (ex.getCause() instanceof ConstraintViolationException) 			
			datail = "Exception: " + ex.toString();
		
		return new ResponseEntity<ResponseErrorDTO>(getErrorDTO(dafaultMsg, ex.getClass().getSimpleName(), datail, HttpStatus.INTERNAL_SERVER_ERROR), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ MappingException.class, PropertyReferenceException.class} )
	public final ResponseEntity<ResponseErrorDTO> handleMappingExceptions(Exception ex) {
		ex.printStackTrace();
		return new ResponseEntity<ResponseErrorDTO>(getErrorDTO(dafaultMsg, ex.getClass().getSimpleName(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ResponseErrorDTO> handleGeneralExceptions(Exception ex, WebRequest request) {
		
		String type = new String("Exception");
		
		if (ex.getCause() instanceof ConnectException) {
			
			String path = ((ServletWebRequest)request).getRequest().getServletPath().toString();
			
									
			type = new String("ConnectException");						
			return new ResponseEntity<ResponseErrorDTO>(getErrorDTO(dafaultMsg2, type, ex.getMessage(), HttpStatus.BAD_REQUEST), new HttpHeaders(), HttpStatus.BAD_REQUEST);		
		}
				
		ex.printStackTrace();
		return new ResponseEntity<ResponseErrorDTO>(getErrorDTO(dafaultMsg, type, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<ResponseErrorDTO> handleRuntimeExceptions(RuntimeException ex) {
		String type = new String("RuntimeException");
		ex.printStackTrace();
		return new ResponseEntity<ResponseErrorDTO>(getErrorDTO(dafaultMsg, type, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(AuthenticationException.class)
	public final ResponseEntity<ResponseErrorDTO> invalidTokenRequestExceptionExceptions(Exception  ex) {
		String msg = dafaultMsg;
		ex.printStackTrace();
		
		if (ex instanceof BadCredentialsException)
			msg = ex.getMessage();
		
		return new ResponseEntity<ResponseErrorDTO>(getErrorDTO(msg, ex.getClass().getSimpleName(), ex.getMessage(), HttpStatus.UNAUTHORIZED), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
	}
	
	// @ExceptionHandler(TokenInvalidRequestException.class)
	// public final ResponseEntity<ResponseErrorDTO> handleInvalidTokenRequestException(TokenInvalidRequestException  ex) {
	// 	ex.printStackTrace();
	// 	return new ResponseEntity<ResponseErrorDTO>(getErrorDTO(dafaultMsg, ex.getClass().getSimpleName(), ex.getMessage(), HttpStatus.UNAUTHORIZED), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
	// }
	
	// @ExceptionHandler(TokenExpiredRequestException.class)
	// public final ResponseEntity<ResponseErrorDTO> handleTokenExpiredRequestException(TokenExpiredRequestException  ex) {
	// 	ex.printStackTrace();
	// 	return new ResponseEntity<ResponseErrorDTO>(getErrorDTO(dafaultMsg, ex.getClass().getSimpleName(), ex.getMessage(), HttpStatus.UNAUTHORIZED), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
	// }
	
	// @ExceptionHandler(TokenAuthorizationRequestException.class)
	// public final ResponseEntity<ResponseErrorDTO> handleTokenAuthorizationRequestException(TokenAuthorizationRequestException  ex) {	
	// 	ex.printStackTrace();
	// 	return new ResponseEntity<ResponseErrorDTO>(getErrorDTO(dafaultMsg, ex.getClass().getSimpleName(), ex.getMessage(), HttpStatus.FORBIDDEN), new HttpHeaders(), HttpStatus.FORBIDDEN);
	// }
	
	// @ExceptionHandler(SessionExpiredException.class)
	// public final ResponseEntity<ResponseErrorDTO> handleSessionExpiredException(SessionExpiredException  ex) {
	// 	String msg = ex.getMessage();		
	// 	ex.printStackTrace();
	// 	return new ResponseEntity<ResponseErrorDTO>(getErrorDTO(msg, ex.getClass().getSimpleName(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	// }
	
	// @ExceptionHandler({BusinessTokenInvalidRequestException.class})
	// public final ResponseEntity<ResponseErrorDTO> handleBusinessTokenInvalidRequestException(BusinessTokenInvalidRequestException  ex) {
	// 	String msg = ex.getMessage()!= null ? ex.getMessage() : dafaultMsg;
	// 	ex.printStackTrace();
	// 	return new ResponseEntity<ResponseErrorDTO>(getErrorDTO(msg, ex.getClass().getSimpleName(), ex.getMessage(), HttpStatus.BAD_REQUEST), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	// }
	
	// @ExceptionHandler({BusinessGenericException.class})
	// public final ResponseEntity<ResponseErrorDTO> handleBusinessTokenInvalidRequestException(BusinessGenericException  ex) {
	// 	ex.printStackTrace();
	// 	String msg = ex.getMessage()!= null ? ex.getMessage() : dafaultMsg;
	// 	return new ResponseEntity<ResponseErrorDTO>(getErrorDTO(msg, ex.getClass().getSimpleName(), ex.getMessage(), HttpStatus.BAD_REQUEST), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	// }
	
	// @ExceptionHandler({BusinessIntegrationException.class})
	// public final ResponseEntity<ResponseErrorDTO> handleBusinessIntegrationException(BusinessTokenInvalidRequestException  ex) {
	// 	ex.printStackTrace();
	// 	String msg = ex.getMessage()!= null ? ex.getMessage() : dafaultMsg;
	// 	return new ResponseEntity<ResponseErrorDTO>(getErrorDTO(msg, ex.getClass().getSimpleName(), ex.getMessage(), HttpStatus.BAD_REQUEST), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	// }
	
	// @ExceptionHandler({ContractsAccessException.class})
	// public final ResponseEntity<ResponseErrorDTO> handleBusinessTokenInvalidRequestException(ContractsAccessException  ex) {
	// 	ex.printStackTrace();
	// 	String msg = ex.getMessage()!= null ? ex.getMessage() : dafaultMsg;
	// 	return new ResponseEntity<ResponseErrorDTO>(getErrorDTO(msg, ex.getClass().getSimpleName(), ex.getMessage(), HttpStatus.BAD_REQUEST), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	// }
	
	// @ExceptionHandler(ApiDataInvalidException.class)
	// public ResponseEntity<ResponseErrorDTO> handleApiDataInvalidErrors(RequestDataInvalidException ex) {
	// 	ex.printStackTrace();
	// 	String msg = ex.getMessage() != null ? ex.getMessage() : dafaultMsg;
		
	// 	String datail = ex.getCause()!= null ? ex.getCause().getMessage() : "";
							
	// 	return new ResponseEntity<ResponseErrorDTO>(getErrorDTO(msg, ex.getClass().getSimpleName(), datail, HttpStatus.BAD_REQUEST), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	// }
	
	// @ExceptionHandler({RequestDuplicationException.class})
	// public final ResponseEntity<ResponseErrorDTO> handleRequestDuplicationException(RequestDuplicationException  ex) {
	// 	ex.printStackTrace();
	// 	String msg = ex.getMessage()!= null ? ex.getMessage() : dafaultMsg;
	// 	return new ResponseEntity<ResponseErrorDTO>(getErrorDTO(msg, ex.getClass().getSimpleName(), ex.getMessage(), HttpStatus.BAD_REQUEST), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	// }
	
	
	private ResponseErrorDTO getErrorDTO(String details, String type, String msg, HttpStatus httpStatus) {
		
		return new ResponseErrorDTO(httpStatus.value(), type,  new Date(), msg, details);
	}
	
	private ResponseErrorValidatorDTO getErrorValidatorDTO(List<String> list, HttpStatus httpStatus) {
		return new ResponseErrorValidatorDTO(httpStatus.value(), new Date(), list);
	}

}