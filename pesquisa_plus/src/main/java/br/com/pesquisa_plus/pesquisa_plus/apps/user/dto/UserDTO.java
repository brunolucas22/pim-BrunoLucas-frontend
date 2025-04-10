package br.com.pesquisa_plus.pesquisa_plus.apps.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
// Imports
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.stereotype.Component;

import br.com.pesquisa_plus.pesquisa_plus.apps.user.models.UserModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// Annotations for the dto
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
// Class UserDTO for the user create and update operations
public class UserDTO {

    // ID of User
    private Long id;

    // Name of User
    @NotBlank(message = "Nome do usuário é obrigatório!")
    @Size(max = 255, message = "Nome do usuário deve ter no máximo 255 caracteres!")
    private String nameUser;

    // Email of User
    @NotBlank(message = "Email do usuário é obrigatório!")
    @Email(message = "Informe um email válido!")
    @Size(max = 255, message = "Email do usuário deve ter no máximo 255 caracteres!")
  
    private String emailUser;

    // Cpf of User
    @NotBlank(message = "CPF do usuário é obrigatório!")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato '000.000.000-00'!")
 
    private String cpfUser;

    // Phone of user
    @NotBlank(message = "Telefone é obrigatório!")
    @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{5}-\\d{4}", message = "Telefone deve estar no formato '(XX) 99999-9999'!")
    private String phoneUser;

    // Start Date of user
    @NotNull(message = "Tipo do usuário é obrigatório!")
    @Min(value = 1, message = "Tipo deve ser no mínimo 1")
    @Max(value = 3, message = "Tipo deve ser no máximo 3")
    private Integer typeUser;

    private String photoUser;

    private Integer statusUser;

   
}
