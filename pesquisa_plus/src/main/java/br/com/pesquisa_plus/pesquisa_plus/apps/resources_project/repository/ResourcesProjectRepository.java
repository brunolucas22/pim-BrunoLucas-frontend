package br.com.pesquisa_plus.pesquisa_plus.apps.resources_project.repository;

import br.com.pesquisa_plus.pesquisa_plus.apps.resources_project.models.ResourcesProjectModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Annotations for the repository
@Repository
// Database access interface
public interface ResourcesProjectRepository extends BaseJpaRepository<ResourcesProjectModel, Integer> {

    // FindAll returns
    Optional<ResourcesProjectModel> findByIdProjectAndIdResource(Long idProject, Long idResource);

}