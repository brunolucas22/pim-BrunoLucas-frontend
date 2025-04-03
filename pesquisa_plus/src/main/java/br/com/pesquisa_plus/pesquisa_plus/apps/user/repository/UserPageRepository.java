package br.com.pesquisa_plus.pesquisa_plus.apps.user.repository;

// Imports
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.models.UserModel;

// Annotations for the repository
@Repository
// Database access interface
public interface UserPageRepository extends PagingAndSortingRepository<UserModel, Long> {

    // FindAll returns
    Page<UserModel> findAll(Specification<?> specification, Pageable pageable);

}