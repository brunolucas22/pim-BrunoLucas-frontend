package br.com.pesquisa_plus.pesquisa_plus.apps.resources.repository;

import java.util.Optional;

// Imports
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import br.com.pesquisa_plus.pesquisa_plus.apps.resources.models.ResourcesModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.repository.BaseJpaRepository;

// Annotations for the repository
@Repository
// Database access interface
public interface ResourcesRepository extends BaseJpaRepository<ResourcesModel, Integer> {

    // FindAll returns
    Optional<ResourcesModel> findByNameResources(String nameResources);

}