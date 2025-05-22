package br.com.pesquisa_plus.pesquisa_plus.apps.resources.models;

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
@Table(name = "resources")
@Component
// Class model for the Resources entity
public class ResourcesModel implements Cloneable, Serializable {

    
    // ID do recurso (chave primária)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome do recurso
    @Column(name = "name_resources", length = 255, nullable = false)
    @JsonProperty("name_resources")
    private String nameResources;

    // Descrição do recurso
    @Column(name = "description_resources", length = 10000, nullable = false)
    @JsonProperty("description_resources")
    private String descriptionResources;

    // Tipo do recurso (ex: notebook, software)
    @Column(name = "type_resources", length = 255, nullable = false)
    @JsonProperty("type_resources")
    private String typeResources;

    // Origem do recurso (comprado, emprestado, doado)
    @Column(name = "origin_resources", length = 255, nullable = false)
    @JsonProperty("origin_resources")
    private String originResources;

    // Valor estimado do recurso
    @Column(name = "estimated_value_resources", precision = 10, scale = 2, nullable = false)
    @JsonProperty("estimated_value_resources")
    private BigDecimal estimatedValueResources;

    // Data de aquisição do recurso
    @Column(name = "aquisition_date_resources", nullable = false)
    @JsonProperty("aquisition_date_resources")
    private LocalDate aquisitionDateResources;

    // Status do recurso (ativo, inativo, em uso, etc.)
    @Column(name = "status_resources", nullable = false)
    @JsonProperty("status_resources")
    private String statusResources;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameResources() {
        return nameResources;
    }

    public void setNameResources(String nameResources) {
        this.nameResources = nameResources;
    }

    public String getDescriptionResources() {
        return descriptionResources;
    }

    public void setDescriptionResources(String descriptionResources) {
        this.descriptionResources = descriptionResources;
    }

    public String getTypeResources() {
        return typeResources;
    }

    public void setTypeResources(String typeResources) {
        this.typeResources = typeResources;
    }

    public String getOriginResources() {
        return originResources;
    }

    public void setOriginResources(String originResources) {
        this.originResources = originResources;
    }

    public BigDecimal getEstimatedValueResources() {
        return estimatedValueResources;
    }

    public void setEstimatedValueResources(BigDecimal estimatedValueResources) {
        this.estimatedValueResources = estimatedValueResources;
    }

    public LocalDate getAquisitionDateResources() {
        return aquisitionDateResources;
    }

    public void setAquisitionDateResources(LocalDate aquisitionDateResources) {
        this.aquisitionDateResources = aquisitionDateResources;
    }

    public String getStatusResources() {
        return statusResources;
    }

    public void setStatusResources(String statusResources) {
        this.statusResources = statusResources;
    }

    // equals e hashCode

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ResourcesModel other = (ResourcesModel) obj;
        return Objects.equals(id, other.id);
    }
}