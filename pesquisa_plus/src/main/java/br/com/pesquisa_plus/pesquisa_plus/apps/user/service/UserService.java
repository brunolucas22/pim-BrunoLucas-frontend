package br.com.pesquisa_plus.pesquisa_plus.apps.user.service;

// Imports
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import br.com.pesquisa_plus.pesquisa_plus.core.mail.service.EmailService;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.PageableDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.RespostaDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.models.Filter;
import br.com.pesquisa_plus.pesquisa_plus.shared.repository.FilterSpecification;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.dto.UserDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.dto.UserListDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.models.UserModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.repository.UserPageRepository;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.repository.UserRepository;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.utils.GeneratorPassword;
import jakarta.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// Annotations for the service
@Service
@Validated
// Class of the access interface between business rules and bank queries
public class UserService {

    // Add dependencies
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserPageRepository userPageRepository;
    @Autowired
    private EmailService emailService;
    private final Validator validator;

    public UserService(Validator validator) {
        this.validator = validator;
    }

    // Method to retrieve paginated and sorted data based on filters and sort order
    public PageableDTO userList(int page, String sortField, int sortOrder, List<Filter> filters) {

        // Define the Page object to hold the result
        Page<UserModel> userPage;

        // Create a Pageable object with sorting if sortOrder is specified
        Pageable pageable;

        // Determine the sort order and field
        if (sortOrder == 0) {

            // No sorting requested, only pagination
            pageable = PageRequest.of(page, 5);

        } else {

            // Sorting is requested, prepare Sort object
            Sort sort = (sortOrder == 1) ? Sort.by(sortField.replace("_user", "")).ascending()
                    : Sort.by(sortField.replace("_user", "")).descending();

            // Create Pageable object with sort parameters
            pageable = PageRequest.of(page, 5, sort);
        }

        try {

            // Retrieve the paginated and sorted data using the repository with specified
            // filters
            userPage = userPageRepository.findAll(FilterSpecification.withFilters(filters, "_user"), pageable);

        } catch (Exception e) {

            // Handle any potential exceptions
            throw new RuntimeException("Error fetching paginated users", e);
        }

        // List dto pages
        List<UserListDTO> user = new ArrayList<>();

        // Extract content from the paginated result
        for (UserModel u : userPage.getContent()) {
            UserListDTO dto = new UserListDTO();
            dto.convertModelToDto(u);
            user.add(dto);
        }

        // Create a PageableDTO object to hold the result data
        PageableDTO data = new PageableDTO();

        // Return the result data
        return data;
    }

    // Method that is used to detail a user
    public ResponseEntity<?> userDetail(long pk) {

        // Get the user by ID ( Primary Key )
        Optional<UserModel> user = userRepository.findById(pk);

        // If user is found
        if (user.isPresent()) {

            // Converting the user to UserDTO
            UserDTO userDto = new UserDTO();
            userDto.convertModelToDto(user.get());

            // Return the result data
            return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK); 

        }

        // Return the message not found
        return new ResponseEntity<RespostaDTO>(new RespostaDTO("Usuário não encontrado na base de dados!"), HttpStatus.OK);

    }

    // Method that registers the user in the system
    public ResponseEntity<?> userCreate(UserDTO user) {

        // Validating the fields of the UserDTO
        var violations = validator.validate(user);
        if (!violations.isEmpty()) {

            // Collecting all validation error messages
            List<String> errorMessage = violations.stream()
                    .map(violation -> violation.getMessage())
                    .collect(Collectors.toList());

            // Returning a ResponseEntity with BAD_REQUEST status and error messages
            return new ResponseEntity<>(new RespostaDTO(errorMessage), HttpStatus.BAD_REQUEST);
        }

        // Converting UserDTO to UserModel
        UserModel userModel = user.convertDtoToModel();

        // Genetare random password for the user
        String password = GeneratorPassword.generate(8);
        userModel.setPassword(password);

        // Saving the user to the repository
        UserModel userCreated = userRepository.save(userModel);

        // Converting the saved UserModel back to UserDTO
        UserDTO userDto = new UserDTO();
        userDto.convertModelToDto(userCreated);

        // Send email to the user
        // emailService.sendEmailText(userCreated.getEmail(), "Usuário Cadastrado com sucesso!",
        //         "Sucesso ao criar seu usuário no sistema!");

        // Returning a ResponseEntity with CREATED status and the created UserDTO
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    // Method that is used to delete a user
    public ResponseEntity<?> userDelete(long pk) {

        // Get the user by ID ( Primary Key )
        Optional<UserModel> user = userRepository.findById(pk);

        // If user is found
        if (user.isPresent()) {

            // Delete the user
            userRepository.deleteById(pk);

            // Return the message sucessfully
            return new ResponseEntity<RespostaDTO>(new RespostaDTO("Projeto deletado com sucesso!"), HttpStatus.OK);

        }

        // Return the message not found
        return new ResponseEntity<RespostaDTO>(new RespostaDTO("Projeto não encontrado na base de dados!"), HttpStatus.OK);
    }    
    
}
