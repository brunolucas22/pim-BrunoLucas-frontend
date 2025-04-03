package br.com.pesquisa_plus.pesquisa_plus.apps.user.repository;

// Imports
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.models.UserModel;

// Annotations for the repository
@Repository
// Database access interface
public interface UserRepository extends CrudRepository<UserModel, Long> {

    // Retrieving the user according to the email
    UserModel findByEmail(String email);

    // Recovering the user according to CPF
    UserModel findByCpf(String cpf);
    
}
