package br.com.pesquisa_plus.pesquisa_plus.apps.project.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

// Annotations for the model
@Entity
@Table(name = "project_with_status")
@Component
@Immutable
// Class model for the Project entity
public class ProjectStatusModel implements Cloneable, Serializable {

    // ID of Project ( Primary Key )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name of Project
    @Column(name = "name_project", length = 255, nullable = false, unique = false)
    @JsonProperty("name_project")
    private String nameProject;

    // Value of Project
    @Column(name = "value_project", precision = 10, scale = 2, nullable = false, unique = false)
    @JsonProperty("value_project")
    private BigDecimal valueProject;

    // Duration of Project
    @Column(name = "duration_project", nullable = false, unique = false)
    @JsonProperty("duration_project")
    private Integer durationProject;

    // Description of Project
    @Column(name = "description_project", length = 1000, nullable = false, unique = false)
    @JsonProperty("description_project")
    private String descriptionProject;

    // Start Date of Project
    @Column(name = "start_date_project", nullable = false, unique = false)
    @JsonProperty("start_date_project")
    private LocalDate startDateProject;

    // Final Date of Project
    @Column(name = "final_date_project", nullable = true, unique = false)
    @JsonProperty("final_date_project")
    private LocalDate finalDateProject;

    // Expected Final Date of Project
    @Column(name = "expected_final_date_project", nullable = false, unique = false)
    @JsonProperty("expected_final_date_project")
    private LocalDate expectedFinalDateProject;

	@Column(name = "status_manual_project", length = 255, nullable = true, unique = false)
	@JsonProperty("status_manual_project")
	private String statusManualProject;

	@Column(name = "status_project", length = 255, nullable = true, unique = false)
	@JsonProperty("status_project")
	private String statusProject;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameProject() {
		return nameProject;
	}

	public void setNameProject(String nameProject) {
		this.nameProject = nameProject;
	}

	public BigDecimal getValueProject() {
		return valueProject;
	}

	public void setValueProject(BigDecimal valueProject) {
		this.valueProject = valueProject;
	}

	public Integer getDurationProject() {
		return durationProject;
	}
	
	public String getDescriptionProject() {
		return descriptionProject;
	}
	
	public void setDescriptionProject(String descriptionProject) {
		this.descriptionProject = descriptionProject;
	}


	public void setDurationProject(Integer durationProject) {
		this.durationProject = durationProject;
	}

	public LocalDate getStartDateProject() {
		return startDateProject;
	}

	public void setStartDateProject(LocalDate startDateProject) {
		this.startDateProject = startDateProject;
	}
	public LocalDate getFinalDateProject() {
		return finalDateProject;
	}

	public void setFinalDateProject(LocalDate finalDateProject) {
		this.finalDateProject = finalDateProject;
	}

	public LocalDate getExpectedFinalDateProject() {
		return expectedFinalDateProject;
	}

	public void setExpectedFinalDateProject(LocalDate expectedFinalDateProject) {
		this.expectedFinalDateProject = expectedFinalDateProject;
	}

	public String getStatusManualProject() {
		return statusManualProject;
	}

	public void setStatusManualProject(String statusManualProject) {
		this.statusManualProject = statusManualProject;
	}

	public String getStatusProject() {
		return statusProject;
	}

	public void setStatusProject(String statusProject) {
		this.statusProject = statusProject;
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
	    ProjectStatusModel other = (ProjectStatusModel) obj;
	    if (id != other.id)
			return false;
	    return true;
	}
}
