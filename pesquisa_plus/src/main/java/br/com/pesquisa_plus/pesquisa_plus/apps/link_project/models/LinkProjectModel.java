package br.com.pesquisa_plus.pesquisa_plus.apps.link_project.models;

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
@Table(name = "link_project")
@Component
// Class model for the Link entity
public class LinkProjectModel implements Cloneable, Serializable {

    // ID of Link ( Primary Key )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Title of Link 
    @Column(name = "title_link_project", nullable = false, unique = false)
    @JsonProperty("title_link_project")
    private String titleLinkProject;

    // Description of Link
    @Column(name = "description_link_project", length = 1000, nullable = false, unique = false)
    @JsonProperty("description_link_project")
    private String descriptionLinkProject;

	// Link 
    @Column(name = "link_link_project", nullable = false, unique = false)
    @JsonProperty("link_link_project")
    private String linkLinkProject;

    // Project
    @Column(name = "id_project", nullable = false, unique = false)
    @JsonProperty("id_project")
    private Long idProject;
    

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

	public String getTitleLinkProject() {
		return titleLinkProject;
	}

	public void setTitleLinkProject(String titleLinkProject) {
		this.titleLinkProject = titleLinkProject;
	}

	public String getLinkLinkProject() {
		return linkLinkProject;
	}

	public void setLinkLinkProject(String linkLinkProject) {
		this.linkLinkProject = linkLinkProject;
	}

	public String getDescriptionLinkProject() {
		return descriptionLinkProject;
	}

	public void setDescriptionLinkProject(String descriptionLinkProject) {
		this.descriptionLinkProject = descriptionLinkProject;
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
	    LinkProjectModel other = (LinkProjectModel) obj;
	    if (id != other.id)
			return false;
	    return true;
	}
}
