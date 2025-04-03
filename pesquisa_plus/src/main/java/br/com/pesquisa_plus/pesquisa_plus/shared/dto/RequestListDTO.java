package br.com.pesquisa_plus.pesquisa_plus.shared.dto;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;

import com.fasterxml.jackson.annotation.JsonRootName;

import br.com.pesquisa_plus.pesquisa_plus.shared.repository.specs.GenericSpecification;
import br.com.pesquisa_plus.pesquisa_plus.shared.repository.specs.SearchCriteria;
import br.com.pesquisa_plus.pesquisa_plus.shared.repository.specs.SearchOperation;
import jakarta.validation.Valid;

//@JsonRootName(value = "RequestListDTO")
public class RequestListDTO extends AbstractDTO implements Serializable {

	@Valid
	PageableDTO pageableDTO;
	
	public RequestListDTO () {}

	
	public PageableDTO getPageableDTO() {
		return pageableDTO;
	}

	public void setPageableDTO(PageableDTO pageableDTO) {
		this.pageableDTO = pageableDTO;
	}

	public Pageable parserToPageable() {

		Sort sort = null;
		String[] listFileds = pageableDTO.getSortField().split(",");
		
		if (listFileds.length == 1) {				
		sort = Sort.by(pageableDTO.getSortOrder().equals("1") ? Sort.Direction.ASC : Sort.Direction.DESC,
				pageableDTO.getSortField());
		}else
		if (listFileds.length > 1) {
			List<String> list = Arrays.asList(listFileds);
			
			String fieldOne = list.get(0).trim();
		
			list = list.subList(1, list.size());
			
			Order orderOne = pageableDTO.getSortOrder().equals("1") ? Order.asc(fieldOne) : Order.desc(fieldOne);
			
			List<Order> listSortOrder = new ArrayList<Order>(); 
			
			listSortOrder.add(orderOne);
			
			listSortOrder.addAll(list.stream().map(entityObject -> Order.by(entityObject.trim()))
					.collect(Collectors.toList()));
		
			sort = Sort.by(listSortOrder);			
			
		}
		return PageRequest.of(pageableDTO.getPage(), pageableDTO.getRows(), sort);
	}

	public boolean isFilter() {
		return this.pageableDTO.getFilters() != null && this.pageableDTO.getFilters().size()>0;
	}

	public Specification parserToCriteria() {
		GenericSpecification genericSpecification = new GenericSpecification();

		if (this.isFilter()) {

			for (PageableFilterDTO filter : this.pageableDTO.getFilters()) {
				genericSpecification.add(new SearchCriteria(filter.getField(), filter.getValue(), SearchOperation.valueOf(filter.getMatchMode())));
			}

			return genericSpecification;

		}
		return genericSpecification;
	}

}