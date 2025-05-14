package br.com.pesquisa_plus.pesquisa_plus.apps.documents_project.models;

import java.io.Serializable;
// Imports
import java.math.BigDecimal;
import java.sql.Timestamp;

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
@Table(name = "documents_project")
@Component
// Class model for the Documents entity
public class DocumentsProjectModel implements Cloneable, Serializable {

    // ID of Documents ( Primary Key )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Title of Documents 
    @Column(name = "title_document_project", nullable = false, unique = false)
    @JsonProperty("title_document_project")
    private String titleDocumentProject;

    // Description of Document
    @Column(name = "description_document_project", length = 1000, nullable = false, unique = false)
    @JsonProperty("description_document_project")
    private String descriptionDocumentProject;

	// Documents 
    @Column(name = "document_document_project", nullable = false, unique = false)
    @JsonProperty("document_document_project")
    private String documentDocumentProject;

    // Project
    @Column(name = "id_project", nullable = false, unique = false)
    @JsonProperty("id_project")
    private Long idProject;

	@JsonProperty("date_time")
    private Timestamp dateTime;
    

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

	public String getTitleDocumentProject() {
		return titleDocumentProject;
	}

	public void setTitleDocumentProject(String titleDocumentProject) {
		this.titleDocumentProject = titleDocumentProject;
	}

	public String getDocumentDocumentProject() {
		return documentDocumentProject;
	}

	public void setDocumentDocumentProject(String documentDocumentProject) {
		this.documentDocumentProject = documentDocumentProject;
	}

	public String getDescriptionDocumentProject() {
		return descriptionDocumentProject;
	}

	public void setDescriptionDocumentProject(String descriptionDocumentProject) {
		this.descriptionDocumentProject = descriptionDocumentProject;
	}
	
	 public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
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
	    DocumentsProjectModel other = (DocumentsProjectModel) obj;
	    if (id != other.id)
			return false;
	    return true;
	}
}
