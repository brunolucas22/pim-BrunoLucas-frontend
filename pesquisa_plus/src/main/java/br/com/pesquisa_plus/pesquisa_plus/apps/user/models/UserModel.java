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

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

// Annotations for the model
@Entity
@Table(name = "users")
@Component
// Class model for the User entity
public class UserModel implements UserDetails, Cloneable, Serializable {

    // ID of User ( Primary Key )
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name of User
    @Column(name = "name_user", length = 255, nullable = false, unique = false)
    @JsonProperty("name_user")
    private String nameUser;

    // Email of User
    @Column(name = "email_user", length = 255, nullable = false, unique = true)
    @JsonProperty("email_user")
    private String emailUser;

    // CPF of User
    @Column(name = "cpf_user", length = 14, nullable = false, unique = true)
    @JsonProperty("cpf_user")
    private String cpfUser;

    // Phone of User
    @Column(name = "phone_user", length = 15, nullable = false, unique = false)
    @JsonProperty("phone_user")
    private String phoneUser;

    // Password of User
    @Column(name = "password_user", length = 255, nullable = false, unique = false)
    @JsonProperty("password_user")
    private String passwordUser;

    // Photo of User
    @Column(name = "photo_user", nullable = true, unique = false)
    @JsonProperty("photo_user")
    private String photoUser;

    // Type of User
    @Column(name = "type_user", nullable = false, unique = false)
    @JsonProperty("type_user")
    private Integer typeUser;

    @Column(name = "status_user", nullable = false, unique = false)
    @JsonProperty("status_user")
    private Integer statusUser;
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getNameUser() {
		return nameUser;
	}

	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}

	public String getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}
	
	public String getCpfUser() {
		return cpfUser;
	}

	public void setCpfUser(String cpfUser) {
		this.cpfUser = cpfUser;
	}
	
	public String getPhoneUser() {
		return phoneUser;
	}

	public void setPhoneUser(String phoneUser) {
		this.phoneUser = phoneUser;
	}
	
	public String getPasswordUser() {
		return passwordUser;
	}

	public void setPasswordUser(String passwordUser) {
		this.passwordUser = passwordUser;
	}
	
	public String getPhotoUser() {
		return photoUser;
	}

	public void setPhotoUser(String photoUser) {
		this.photoUser = photoUser;
	}
	
	public Integer getTypeUser() {
		return typeUser;
	}

	public void setTypeUser(Integer typeUser) {
		this.typeUser = typeUser;
	}
	
	public Integer getStatusUser() {
		return statusUser;
	}

	public void setStatusUser(Integer statusUser) {
		this.statusUser = statusUser;
	}

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
        return emailUser;
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
        return passwordUser;
    }

    @Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
			return true;
	    if (obj == null)
			return false;
	    if (getClass() != obj.getClass())
			return false;
	    UserModel other = (UserModel) obj;
	    if (id != other.id)
			return false;
	    return true;
	}
}
