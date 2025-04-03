package br.com.pesquisa_plus.pesquisa_plus.apps.user.models;

// Imports
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// Annotations for the model
@Entity
@Table(name = "users")
@Getter
@Setter
// Class model for the User entity
public class UserModel implements UserDetails {

    // ID of User ( Primary Key )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name of User
    @Column(name = "name_user", length = 255, nullable = false, unique = false)
    @JsonProperty("name_user")
    private String name;

    // Email of User
    @Column(name = "email_user", length = 255, nullable = false, unique = true)
    @JsonProperty("email_user")
    private String email;

    // CPF of User
    @Column(name = "cpf_user", length = 14, nullable = false, unique = true)
    @JsonProperty("cpf_user")
    private String cpf;

    // Phone of User
    @Column(name = "phone_user", length = 15, nullable = false, unique = false)
    @JsonProperty("phone_user")
    private String phone;

    // Password of User
    @Column(name = "password_user", length = 255, nullable = false, unique = false)
    @JsonProperty("password_user")
    private String password;

    // Photo of User
    @Column(name = "photo_user", nullable = true, unique = false)
    @JsonProperty("photo_user")
    private String photo;

    // Type of User
    @Column(name = "type_user", nullable = false, unique = false)
    @JsonProperty("type_user")
    private Integer type;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // This method should return the authorities granted to the user.
        // In this case, it returns null, meaning no authorities are granted.
        return null;
    }

    @Override
    public String getUsername() {
        // This method returns the username used to authenticate the user.
        // Here, it returns the email address of the user.
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // This method indicates whether the user's account has expired.
        // Returning true means the account is not expired and is still valid.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // This method indicates whether the user is locked or unlocked.
        // Returning true means the account is not locked.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // This method indicates whether the user's credentials (password) have expired.
        // Returning true means the credentials are still valid and not expired.
        return true;
    }

    @Override
    public boolean isEnabled() {
        // This method indicates whether the user is enabled or disabled.
        // Returning true means the user is enabled and can be authenticated.
        return true;
    }

    @Override
    public String getPassword() {
        // This method returns the password used to authenticate the user.
        // Here, it returns the user's password.
        return password;
    }

}
