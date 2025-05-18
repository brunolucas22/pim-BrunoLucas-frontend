package br.com.pesquisa_plus.pesquisa_plus.apps.resources_user.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;

// Annotations for the model
@Entity
@Table(name = "resources_user")
@Component
// Class model for the Resources entity
public class ResourcesUserModel implements Cloneable, Serializable {

    // ID of Resources ( Primary Key )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User
    @Column(name = "id_resource", nullable = false, unique = false)
    @JsonProperty("id_resource")
    private Long idResource;

    // User
    @Column(name = "id_user", nullable = false, unique = false)
    @JsonProperty("id_user")
    private Long idUser;
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Long getIdResource() {
		return idResource;
	}

	public void setIdResource(Long idResource) {
		this.idResource = idResource;
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
	    ResourcesUserModel other = (ResourcesUserModel) obj;
	    if (id != other.id)
			return false;
	    return true;
	}
}
