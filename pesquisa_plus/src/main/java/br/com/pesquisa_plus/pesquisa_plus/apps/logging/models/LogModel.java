package br.com.pesquisa_plus.pesquisa_plus.apps.logging.models;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "log")
@Component
public class LogModel implements Cloneable, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "action", nullable = false, unique = false)
    @JsonProperty("action")
    private String action;
    @Column(name = "date_time", nullable = false, unique = false)
    @JsonProperty("date_time")
    private Timestamp dateTime;
    @Column(name = "details", nullable = false, unique = false)
    @JsonProperty("details")
    private String details;
    @Column(name = "controller", nullable = false, unique = false)
    @JsonProperty("controller")
    private String controller;
    @Column(name = "id_user", nullable = false, unique = false)
    @JsonProperty("id_user")
    private String idUser;
     @JsonProperty("id_project")
    private String idProject;
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

    public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

    public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

    public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

    public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

     public String getIdProject() {
		return idProject;
	}

	public void setIdProject(String idProject) {
		this.idProject = idProject;
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
	    LogModel other = (LogModel) obj;
	    if (id != other.id)
			return false;
	    return true;
	}
}