package br.com.pesquisa_plus.pesquisa_plus.shared.models;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractModel<ID extends Serializable> extends AbstractPersistable<ID> {

	private static final long serialVersionUID = 1L;
	
	@Transient
	private String statusTransiente;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public void setId(ID id) {
		super.setId(id);
	}

	@Override
	@JsonIgnore
	public boolean isNew() {
		return null == this.getId();
	}
}