package br.com.pesquisa_plus.pesquisa_plus.apps.documents_user.repository;

import java.util.Optional;

// Imports
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import br.com.pesquisa_plus.pesquisa_plus.apps.documents_user.models.DocumentsUserModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.repository.BaseJpaRepository;

// Annotations for the repository
@Repository
// Database access interface
public interface DocumentsUserRepository extends BaseJpaRepository<DocumentsUserModel, Integer> {

    // FindAll returns
    Optional<DocumentsUserModel> findByTitleDocumentUser(String titleDocumentsUser);
    
    Optional<DocumentsUserModel> findById(Long id);

}