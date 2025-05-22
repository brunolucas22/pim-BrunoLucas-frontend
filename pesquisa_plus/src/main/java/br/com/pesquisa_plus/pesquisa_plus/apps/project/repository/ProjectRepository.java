package br.com.pesquisa_plus.pesquisa_plus.apps.project.repository;

import java.util.Optional;

// Imports
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import br.com.pesquisa_plus.pesquisa_plus.apps.project.models.ProjectModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.repository.BaseJpaRepository;

// Annotations for the repository
@Repository
// Database access interface
public interface ProjectRepository extends BaseJpaRepository<ProjectModel, Integer>,ProjectCustomRepository  {

    // FindAll returns
  Optional<ProjectModel> findByNameProject(String nameProject);




}