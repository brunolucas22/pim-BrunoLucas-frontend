package br.com.pesquisa_plus.pesquisa_plus.apps.user.dto;

import br.com.pesquisa_plus.pesquisa_plus.apps.user.models.UserModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.validations.UniqueUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

// Anotações para o DTO
@Data
// Classe Dto responsável pela inserção de dados do usuário
public class UsuarioAtualizarDTO {

    // Nome do Usuário
    @NotBlank(message = "Nome é obrigatório!")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres!")
    private String nome_usuario;

    // Email do Usuário
    @NotBlank(message = "Email é obrigatório!")
    @Email(message = "Informe um email válido!")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres!")
    @UniqueUsuario(coluna = "Email")
    private String email_usuario;

    // CPF do Usuário
    @NotBlank(message = "CPF é obrigatório!")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato '000.000.000-00'!")
    @UniqueUsuario(coluna = "Cpf")
    private String cpf_usuario;

    // Telefone do Usuário
    @NotBlank(message = "Telefone é obrigatório!")
    @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{5}-\\d{4}", message = "Telefone deve estar no formato '(XX) 99999-9999'!")
    private String telefone_usuario;
    
    // Tipo do Usuário
    @NotNull(message = "Tipo é obrigatório!")
    @Min(value = 1, message = "Tipo deve ser no mínimo 1")
    @Max(value = 3, message = "Tipo deve ser no máximo 3")
    private Integer tipo_usuario;

    // Método que converte o DTO para o modelo usuário
    public UserModel converteDtoParaModelo() {
        UserModel usuario = new UserModel();
        usuario.setName(nome_usuario);
        usuario.setEmail(email_usuario);
        usuario.setCpf(cpf_usuario);
        usuario.setPhone(telefone_usuario);
        usuario.setType(tipo_usuario);
        return usuario;
    }

}

