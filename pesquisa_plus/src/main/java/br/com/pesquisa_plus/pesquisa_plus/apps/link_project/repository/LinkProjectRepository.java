package br.com.pesquisa_plus.pesquisa_plus.apps.link_project.repository;

import java.util.Optional;

// Imports
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import br.com.pesquisa_plus.pesquisa_plus.apps.link_project.models.LinkProjectModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.repository.BaseJpaRepository;

// Annotations for the repository
@Repository
// Database access interface
public interface LinkProjectRepository extends BaseJpaRepository<LinkProjectModel, Integer> {

    // FindAll returns
    Optional<LinkProjectModel> findByTitleLinkProject(String titleLinkProject);

}