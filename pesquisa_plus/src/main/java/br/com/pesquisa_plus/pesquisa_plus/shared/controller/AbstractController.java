package br.com.pesquisa_plus.pesquisa_plus.shared.controller;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.pesquisa_plus.pesquisa_plus.shared.dto.RequestListDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.ResponseListDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.service.AbstractService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:7155", allowedHeaders = "*", allowCredentials = "true")
@RestController
public abstract class AbstractController<T, D, ID extends Serializable> implements ServiceMap<T, D> {
	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected AbstractService<T, ID> abstractService;
	@Autowired
	protected ModelMapper modelMapper;



	private T entityObject;
	private D dtoObject;

	public AbstractController(T entityObject, D dtoObject) {
		this.dtoObject = dtoObject;
		this.entityObject = entityObject;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ResponseEntity<D> selectedByID(@PathVariable("codigo") ID codigo) {

		this.LOGGER.debug(String.format("Requesting record codigo: [%s].", codigo));

		T entityObject = (T) this.abstractService.selectedByID(codigo)
				.orElseThrow(() -> new EntityNotFoundException("Entidade não encontrada."));

		return ResponseEntity.ok(this.convertToDto(entityObject, dtoObject));

	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/list")
	public ResponseListDTO list(@RequestBody @Valid RequestListDTO requestListDTO) {
		this.LOGGER.debug("Requesting all records.");
		

		Long totalElements = this.abstractService.gettotalElements(requestListDTO);

		List<T> listModel = this.abstractService.getAll(requestListDTO).getContent();

		List<D> listDto = (List<D>) listModel.stream().map(entityObject -> this.convertToDto(entityObject, dtoObject))
				.collect(Collectors.toList());

		return new ResponseListDTO(listDto, totalElements);
	}


	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<T> create(@RequestBody @Valid D dtoObject) throws Exception {
		this.LOGGER.debug(String.format("Saving the entity [%s].", dtoObject));

		T entityObjectSaved = this.abstractService.create(this.convertToEntity(dtoObject, entityObject));

		return (ResponseEntity<T>) ResponseEntity.ok(this.convertToDto(entityObjectSaved, dtoObject));
	}

	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<D> update(@RequestBody @Valid D dtoObject) throws Exception{
		this.LOGGER.debug(String.format("Request to update the record [%s].", dtoObject));

		if (dtoObject == null) {
			this.LOGGER.error("Entity can not be null.");
			return null;
		}
		T entityObjectUpdated = this.abstractService.update(this.convertToEntity(dtoObject, entityObject));

		// Optional.ofNullable(entityObjectUpdated).orElseThrow(() -> new
		// EntityNotFoundException("Entidade não encontrada!"));

		return ResponseEntity.ok(this.convertToDto(entityObjectUpdated, dtoObject));
	}


	@DeleteMapping(value = "/{codigo}")
	public void delete(@PathVariable("codigo") ID codigo) throws Exception{
		this.LOGGER.debug(String.format("Request to delete the record [%s].", codigo));

		this.abstractService.deleteByID(codigo);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public D convertToDto(T entityObject, D dtoObject) {
		return (D) modelMapper.map(entityObject, dtoObject.getClass());
	}

	@SuppressWarnings("unchecked")
	@Override
	public D convertMapperToDto(T entityObject, D dtoObject) {

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
		
		return (D) this.modelMapper.map(entityObject, dtoObject.getClass());
	}

	@SuppressWarnings("unchecked")
	@Override
	public T convertToEntity(D dtoObject, T entityObject) {
		return (T) modelMapper.map(dtoObject, entityObject.getClass());
	}

}