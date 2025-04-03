package br.com.pesquisa_plus.pesquisa_plus.apps.logging.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import br.com.pesquisa_plus.pesquisa_plus.apps.logging.dto.LogDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.logging.models.LogModel;

@Repository
public interface LogCustomRepository {

	Page<LogDTO> listView(Specification<LogModel> spec, Pageable pageable, boolean report);
		
}
