package br.com.pesquisa_plus.pesquisa_plus.apps.documents_user.models;

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
@Table(name = "documents_user")
@Component
// Class model for the Documents entity
public class DocumentsUserModel implements Cloneable, Serializable {

    // ID of Documents ( Primary Key )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Title of Documents 
    @Column(name = "title_document_user", nullable = false, unique = false)
    @JsonProperty("title_document_user")
    private String titleDocumentUser;

    // Description of Document
    @Column(name = "description_document_user", length = 1000, nullable = false, unique = false)
    @JsonProperty("description_document_user")
    private String descriptionDocumentUser;

	// Documents 
    @Column(name = "document_document_user", nullable = false, unique = false)
    @JsonProperty("document_document_user")
    private String documentDocumentUser;

    // User
    @Column(name = "id_user", nullable = false, unique = false)
    @JsonProperty("id_user")
    private Long idUser;

	@JsonProperty("date_time")
    private Timestamp dateTime;
    

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

	public String getTitleDocumentUser() {
		return titleDocumentUser;
	}

	public void setTitleDocumentUser(String titleDocumentUser) {
		this.titleDocumentUser = titleDocumentUser;
	}

	public String getDocumentDocumentUser() {
		return documentDocumentUser;
	}

	public void setDocumentDocumentUser(String documentDocumentUser) {
		this.documentDocumentUser = documentDocumentUser;
	}

	public String getDescriptionDocumentUser() {
		return descriptionDocumentUser;
	}

	public void setDescriptionDocumentUser(String descriptionDocumentUser) {
		this.descriptionDocumentUser = descriptionDocumentUser;
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
	    DocumentsUserModel other = (DocumentsUserModel) obj;
	    if (id != other.id)
			return false;
	    return true;
	}
}
