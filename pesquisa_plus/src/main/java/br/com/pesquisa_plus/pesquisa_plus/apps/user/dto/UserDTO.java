package br.com.pesquisa_plus.pesquisa_plus.apps.user.dto;

// Imports
import lombok.Data;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.models.UserModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.validations.UniqueUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// Annotations for the dto
@Data
// Class UserDTO for the user create and update operations
public class UserDTO {

    // ID of User
    private Long id;

    // Name of User
    @NotBlank(message = "Nome do usuário é obrigatório!")
    @Size(max = 255, message = "Nome do usuário deve ter no máximo 255 caracteres!")
    private String name_user;

    // Email of User
    @NotBlank(message = "Email do usuário é obrigatório!")
    @Email(message = "Informe um email válido!")
    @Size(max = 255, message = "Email do usuário deve ter no máximo 255 caracteres!")
    @UniqueUsuario(coluna = "Email")
    private String email_user;

    // Cpf of User
    @NotBlank(message = "CPF do usuário é obrigatório!")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato '000.000.000-00'!")
    @UniqueUsuario(coluna = "Cpf")
    private String cpf_user;

    // Phone of user
    @NotBlank(message = "Telefone é obrigatório!")
    @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{5}-\\d{4}", message = "Telefone deve estar no formato '(XX) 99999-9999'!")
    private String phone_user;

    // Start Date of user
    @NotNull(message = "Tipo do usuário é obrigatório!")
    @Min(value = 1, message = "Tipo deve ser no mínimo 1")
    @Max(value = 3, message = "Tipo deve ser no máximo 3")
    private Integer type_user;

    // Method that converts the Model to the user DTO
    public void convertModelToDto(UserModel user) {
        id = user.getId();
        name_user = user.getName();
        email_user = user.getEmail();
        cpf_user =  user.getCpf();
        phone_user = user.getPhone();
        type_user = user.getType();

    }

    // Method that converts the DTO to the user Model
    public UserModel convertDtoToModel() {
        UserModel project = new UserModel();
        project.setName(name_user);
        project.setEmail(email_user);
        project.setCpf(cpf_user);
        project.setPhone(phone_user);
        project.setType(type_user);
        return project;
    }

}
