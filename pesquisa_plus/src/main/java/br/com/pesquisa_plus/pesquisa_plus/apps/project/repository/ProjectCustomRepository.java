package br.com.pesquisa_plus.pesquisa_plus.apps.project.repository;

import br.com.pesquisa_plus.pesquisa_plus.apps.logging.dto.LogDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.logging.models.LogModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.project.dto.ProjectDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.project.models.ProjectModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface ProjectCustomRepository {
    Page<ProjectDTO> listView(Specification<ProjectModel> spec, Pageable pageable, boolean report);


}

