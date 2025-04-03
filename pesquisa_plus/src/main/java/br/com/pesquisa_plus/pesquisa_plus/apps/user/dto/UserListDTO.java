package br.com.pesquisa_plus.pesquisa_plus.apps.user.dto;

// Imports
import lombok.Data;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.models.UserModel;

// Annotations for the dto
@Data
// Class UserListDTO for the user list operations
public class UserListDTO {

    // ID of User
    private Long id;

    // Name of User
    private String name_user;

    // Email of User
    private String email_user;

    // Cpf of User
    private String cpf_user;

    // Method that converts the Model to the user DTO
    public void convertModelToDto(UserModel user) {
        id = user.getId();
        name_user = user.getName();
        email_user = user.getEmail();
        cpf_user = user.getCpf();
    }
    
}
