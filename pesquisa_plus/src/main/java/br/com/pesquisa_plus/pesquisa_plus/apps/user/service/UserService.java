package br.com.pesquisa_plus.pesquisa_plus.apps.user.service;

// Imports
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import br.com.pesquisa_plus.pesquisa_plus.shared.exception.RequestDataInvalidException;
import br.com.pesquisa_plus.pesquisa_plus.shared.service.AbstractService;

import br.com.pesquisa_plus.pesquisa_plus.apps.user.models.UserModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.repository.UserRepository;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.utils.GeneratorPassword;
import br.com.pesquisa_plus.pesquisa_plus.core.mail.service.EmailService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

// Annotations for the service
@Service
@Validated
// Class of the access interface between business rules and bank queries
public class UserService extends AbstractService<UserModel, Integer> {

    // Add dependencies
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    // Method that registers the user in the system
    public UserModel create(UserModel user) {

    	Optional<UserModel> userModelByCpf = userRepository.findByCpfUser(user.getCpfUser());
    			
    	Optional<UserModel> userModelByEmail = 	Optional.ofNullable(userRepository.findByEmailUser(user.getEmailUser()));

		if (!userModelByCpf.isEmpty() || !userModelByEmail.isEmpty()) {
			throw new RequestDataInvalidException("Entidade já cadastrada!");
		}
       
        String password = GeneratorPassword.generate(8);
        user.setPasswordUser(passwordEncoder.encode(password));
        
        emailService.sendEmailCreatedUser(password, user);
        
     
        return super.create(user);
    }

    
}
