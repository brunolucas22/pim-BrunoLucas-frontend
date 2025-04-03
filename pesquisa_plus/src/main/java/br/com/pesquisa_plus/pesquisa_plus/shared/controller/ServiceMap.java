package br.com.pesquisa_plus.pesquisa_plus.shared.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "*")
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public interface ServiceMap <T, D>{
	
	public D convertToDto(T entity, D dto);
	public D convertMapperToDto(T entity, D dto);
	public T convertToEntity(D dto, T entity);
}