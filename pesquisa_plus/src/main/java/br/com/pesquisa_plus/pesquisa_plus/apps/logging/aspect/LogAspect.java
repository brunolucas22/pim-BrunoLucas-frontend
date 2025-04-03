package br.com.pesquisa_plus.pesquisa_plus.apps.logging.aspect;




import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.pesquisa_plus.pesquisa_plus.apps.logging.models.LogModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.logging.service.LogService;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.models.UserModel;
import jakarta.servlet.http.HttpServletRequest;


@Aspect
@Component
public class LogAspect {

	 @Autowired
	    private LogService logService;

	    @Autowired
	    private ObjectMapper objectMapper;
	    
	    @Autowired
	    private HttpServletRequest request;

	    @AfterReturning("execution(* br.com.pesquisa_plus.pesquisa_plus.shared.controller.AbstractController.*(..))")
	    public void registerLog(JoinPoint joinPoint) {
	        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	        HttpServletRequest request = attributes.getRequest();
	        String method = request.getMethod();
	        System.out.println("antes do if");
	        if (method.equals("POST") || method.equals("PUT") || method.equals("DELETE")) {
	        	  System.out.println("é mutate");
	        	  String path = request.getRequestURI();
	            if (!path.contains("list")) {
	            	 
	                  String action = joinPoint.getSignature().getName();
	                  String controller = joinPoint.getTarget().getClass().getSimpleName().toLowerCase();

	                 
	                  String requestBody = convertArgsToJson(joinPoint.getArgs());

	                  
	                  if (requestBody.contains("Erro ao converter dados")) {
	                      System.err.println("🚨 Falha ao converter dados para JSON no método: " + action);
	                      System.err.println("🔍 Argumentos recebidos: " + Arrays.toString(joinPoint.getArgs()));
	                  }

	                  
	                  String detail = String.format("{\"data\":%s}", requestBody);
	                  
	                  String idProject = getPathVariables().get("idProject");
	                  
	                  LogModel log = new LogModel();
	                  
	                  Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	          		if (principal instanceof UserModel) { 
	          		    Long userId = ((UserModel) principal).getId();
	          		    log.setIdUser("" + userId);
	          		}
	                  
	                
	                  log.setAction(action);
	                  log.setDateTime(Timestamp.from(Instant.now()));
	                  log.setDetails(detail);
	                  log.setController(controller);
	                  log.setIdProject(idProject);
	                  logService.create(log);
	            }
	        }
	    }

	    private String convertArgsToJson(Object[] args) {
	    	ObjectMapper objectMapper = new ObjectMapper();

	     
	        objectMapper.findAndRegisterModules();

	        
	        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

	        try {
	           
	            return objectMapper.writeValueAsString(args);
	        } catch (JsonProcessingException e) {
	            return "\"Erro ao converter dados para JSON: " + e.getMessage() + "\"";
	        }
	    	
	    }
	    
	    @SuppressWarnings("unchecked")
		private Map<String, String> getPathVariables() {
	        return (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
	    }

	    
}