package br.com.pesquisa_plus.pesquisa_plus.apps.user.repository;

import java.util.Optional;

// Imports
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.com.pesquisa_plus.pesquisa_plus.apps.user.dto.UserDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.models.UserModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.repository.BaseJpaRepository;

// Annotations for the repository
@Repository
// Database access interface
public interface UserRepository extends BaseJpaRepository<UserModel, Integer> {

    // Retrieving the user according to the email
    UserModel findByEmailUser(String email);
    

    // Recovering the user according to CPF
    Optional<UserModel> findByCpfUser(String cpf);
    
    Optional<UserModel> findById(Long id);
    
}
