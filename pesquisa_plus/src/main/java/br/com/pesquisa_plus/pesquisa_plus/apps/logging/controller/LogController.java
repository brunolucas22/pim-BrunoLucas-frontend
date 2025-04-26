package br.com.pesquisa_plus.pesquisa_plus.apps.logging.controller;

// Imports
import org.springframework.web.bind.annotation.RestController;


import br.com.pesquisa_plus.pesquisa_plus.apps.logging.dto.LogDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.logging.models.LogModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.logging.service.LogService;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.models.UserModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.controller.AbstractController;
import br.com.pesquisa_plus.pesquisa_plus.shared.controller.ServiceMap;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.PageableFilterDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.RequestListDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.ResponseListDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.service.AbstractService;
import jakarta.validation.Valid;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

// Annotations for the controller
@CrossOrigin(origins = "http://localhost:7155", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping(value = "/logs")
// User access interface
public class LogController implements ServiceMap<LogModel, LogDTO> {
	
	@Autowired
	protected LogService abstractService;
	
	@Autowired
	protected ModelMapper modelMapper;
	
	private LogModel logModel;
	private LogDTO logDTO;
	
	public LogController(LogModel logModel, LogDTO logDTO) {
		this.logDTO = logDTO;
		this.logModel = logModel;
	
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/list/{idProject}")
	public ResponseListDTO list(@RequestBody @Valid RequestListDTO requestListDTO,  @PathVariable("idProject") String idProject) {

		// requestListDTO.getPageableDTO().getFilters()
		// .add(new PageableFilterDTO("idProject", "" 
		// 		+ idProject,
		// 		"EQUAL"));
		
		
		// Long totalElements = this.abstractService.gettotalElements(requestListDTO);

		// List<LogModel> listModel = this.abstractService.getAll(requestListDTO).getContent();

		// List<LogDTO> listDto = (List<LogDTO>) listModel.stream().map(logModel -> this.convertToDto(logModel, logDTO)).collect(Collectors.toList());

		// return new ResponseListDTO(listDto, totalElements);

		requestListDTO.getPageableDTO().getFilters()
		.add(new PageableFilterDTO("idProject", "" 
				+ idProject,
				"EQUAL"));
		
			
		Page<LogDTO> listModel = abstractService.getAllView(requestListDTO);

		return new ResponseListDTO<LogDTO>(listModel.getContent(), listModel.getTotalElements());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LogDTO convertToDto(LogModel entityObject, LogDTO dtoObject) {
		return (LogDTO) modelMapper.map(entityObject, dtoObject.getClass());
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public LogDTO convertMapperToDto(LogModel entityObject, LogDTO dtoObject) {

		try {
			Method createConvertMap = dtoObject.getClass().getDeclaredMethod("createConvertMap", ModelMapper.class);

			this.modelMapper = (ModelMapper) createConvertMap.invoke(dtoObject, this.modelMapper);

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
		
		return (LogDTO) this.modelMapper.map(entityObject, dtoObject.getClass());
	}

	@SuppressWarnings("unchecked")
	@Override
	public LogModel convertToEntity(LogDTO dtoObject, LogModel entityObject) {
		return (LogModel) modelMapper.map(dtoObject, entityObject.getClass());
	}
}