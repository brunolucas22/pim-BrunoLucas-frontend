package br.com.pesquisa_plus.pesquisa_plus.apps.member_project.models;

import java.io.Serializable;
// Imports
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.stereotype.Component;

// Annotations for the model
@Entity
@Table(name = "member_project")
@Component
// Class model for the Member entity
public class MemberProjectModel implements Cloneable, Serializable {

    // ID of Member ( Primary Key )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "role_member_project", nullable = false, unique = false)
    @JsonProperty("role_member_project")
    private String roleMemberProject;
	 
    @Column(name = "scholarship_member_project", nullable = false, unique = false)
    @JsonProperty("scholarship_member_project")
    private BigDecimal scholarshipMemberProject;
    
	@Column(name = "permissions_member_project", nullable = false, unique = false)
    @JsonProperty("permissions_member_project")
    private Integer[] permissionsMemberProject;

	@Column(name = "id_supervisor", nullable = false, unique = false)
    @JsonProperty("id_supervisor")
    private Long idSupervisor;

	@Column(name = "id_project", nullable = false, unique = false)
    @JsonProperty("id_project")
    private Long idProject;

	@Column(name = "id_user", nullable = false, unique = false)
    @JsonProperty("id_user")
    private Long idUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdProject() {
		return idProject;
	}

	public void setIdProject(Long idProject) {
		this.idProject = idProject;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Long getIdSupervisor() {
		return idSupervisor;
	}

	public void setIdSupervisor(Long idSupervisor) {
		this.idSupervisor = idSupervisor;
	}

	public String getRoleMemberProject() {
		return roleMemberProject;
	}

	public void setRoleMemberProject(String roleMemberProject) {
		this.roleMemberProject = roleMemberProject;
	}

	public BigDecimal getScholarshipMemberProject() {
		return scholarshipMemberProject;
	}

	public void setScholarshipMemberProject(BigDecimal scholarshipMemberProject) {
		this.scholarshipMemberProject = scholarshipMemberProject;
	}

	public Integer[] getPermissionsMemberProject() {
		return permissionsMemberProject;
	}

	public void setPermissionsMemberProject(Integer[] permissionsMemberProject) {
		this.permissionsMemberProject = permissionsMemberProject;
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
	    MemberProjectModel other = (MemberProjectModel) obj;
	    if (id != other.id)
			return false;
	    return true;
	}
}
