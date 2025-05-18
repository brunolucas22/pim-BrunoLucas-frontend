package br.com.pesquisa_plus.pesquisa_plus.apps.resources_user.repository;

import br.com.pesquisa_plus.pesquisa_plus.apps.resources_user.models.ResourcesUserModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Annotations for the repository
@Repository
// Database access interface
public interface ResourcesUserRepository extends BaseJpaRepository<ResourcesUserModel, Integer> {

    // FindAll returns
    Optional<ResourcesUserModel> findByIdUserAndIdResource(Long idUser, Long idResource);

}