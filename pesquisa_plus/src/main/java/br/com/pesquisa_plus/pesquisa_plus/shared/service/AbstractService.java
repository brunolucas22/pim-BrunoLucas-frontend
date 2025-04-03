package br.com.pesquisa_plus.pesquisa_plus.shared.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import br.com.pesquisa_plus.pesquisa_plus.shared.dto.PageableFilterDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.RequestListDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.exception.RequestDataInvalidException;
import br.com.pesquisa_plus.pesquisa_plus.shared.repository.BaseJpaRepository;
import br.com.pesquisa_plus.pesquisa_plus.shared.repository.specs.SearchOperation;


import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.Valid;

@MappedSuperclass
@Transactional(readOnly = true)
public abstract class AbstractService<T, ID extends Serializable> {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BaseJpaRepository<T, ID> baseJpaRepository;


	public Optional<T> selectedByID(ID id) {
		this.LOGGER.debug(String.format("Requesting record id: [%s].", id));

		return this.baseJpaRepository.findById(id);
	}

	@SuppressWarnings("unchecked")
	public Page<T> getAll(@Valid RequestListDTO requestListDTO) {
		this.LOGGER.debug("Requesting all records.");

		requestListDTO = removeEntityZero(requestListDTO);
				
		if (requestListDTO.isFilter())
			return this.baseJpaRepository.findAll(requestListDTO.parserToCriteria(), requestListDTO.parserToPageable());
	
		return this.baseJpaRepository.findAll(requestListDTO.parserToPageable());
	}

	@Transactional
	public T create(T entityObject) {

		this.LOGGER.debug(String.format("Saving the entity [%s].", entityObject));

		if (entityObject == null)
			throw new EntityNotFoundException("Entidade não encontrada.");


		Object entityId = this.baseJpaRepository.getEntityInformation().getId(entityObject);
		
		if (entityId != null) {
		    if (entityId instanceof Long && !entityId.equals(0L)) {
		        throw new RequestDataInvalidException("Entidade com código maior que zero!");
		    } else if (!entityId.equals(0)) { // Caso o ID seja outro tipo de número
		        throw new RequestDataInvalidException("Entidade com código maior que zero!");
		    }
		}

		return this.baseJpaRepository.persist(entityObject);
	}

	@Transactional
	public T update(T entityObject) {
	
		this.LOGGER.debug(String.format("Request to update the record [%s].", entityObject));

		if (entityObject == null)
			throw new EntityNotFoundException("Entidade não encontrada.");

		if((this.baseJpaRepository.getEntityInformation().getId(entityObject) instanceof Long)) {
			if (this.baseJpaRepository.getEntityInformation().getId(entityObject).equals(0L))
				throw new EntityNotFoundException("Entidade não encontrada.");
		} else {
			if (this.baseJpaRepository.getEntityInformation().getId(entityObject).equals(0))
				throw new EntityNotFoundException("Entidade não encontrada.");
		}

		if (!this.selectedByID((ID) this.baseJpaRepository.getEntityInformation().getId(entityObject))
				.isPresent())
			throw new EntityNotFoundException("Entidade não encontrada.");

		return this.baseJpaRepository.merge(entityObject);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public void delete(T entityObject) {
		this.LOGGER.debug(String.format("Request to delete the record [%s].", entityObject));

		if (entityObject == null)
			throw new EntityNotFoundException("Entidade não encontrada.");

		if (this.baseJpaRepository.getEntityInformation().getId(entityObject).equals(0))
			throw new EntityNotFoundException("Entidade não encontrada.");

		Optional<T> entityDelete = this
				.selectedByID((ID) this.baseJpaRepository.getEntityInformation().getId(entityObject));

		if (!entityDelete.isPresent())
			throw new EntityNotFoundException("Entidade não encontrada.");

		this.baseJpaRepository.delete(entityDelete.get());
	}

	@Transactional
	public void deleteByID(ID id) {
		this.LOGGER.debug(String.format("Request to delete the record [%s].", id));

		if (id.equals(0))
			throw new EntityNotFoundException("Entidade não encontrada.");

		if (!this.selectedByID(id).isPresent())
			throw new EntityNotFoundException("Entidade não encontrada.");

		this.baseJpaRepository.deleteById(id);
	}
	
	@Transactional
	public List<T> createList(List<T> listObject) {


		if (listObject == null || listObject.size() == 0)
			throw new EntityNotFoundException("Entidade não encontrada.");

		return this.baseJpaRepository.persistAll(listObject);
	}

	private RequestListDTO removeEntityZero(RequestListDTO requestListDTO) {
		 requestListDTO.getPageableDTO().getFilters().add(new PageableFilterDTO(this.baseJpaRepository.getEntityInformation().getIdAttribute().getName(), "0", SearchOperation.NOT_EQUAL.name()));
		 return requestListDTO;
	}
	
	public long gettotalElements(RequestListDTO requestListDTO) {
		requestListDTO = removeEntityZero(requestListDTO);
		return this.baseJpaRepository.count(requestListDTO.parserToCriteria());
	}

	public BaseJpaRepository<T, ID> getBaseJpaRepository() {
		return baseJpaRepository;
	}
	
	
	
}