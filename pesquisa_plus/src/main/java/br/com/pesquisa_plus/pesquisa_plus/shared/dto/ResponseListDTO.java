package br.com.pesquisa_plus.pesquisa_plus.shared.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class ResponseListDTO<D>  extends AbstractDTO implements Serializable {
	
	private Long totalElements;
	private List<D> data;
	
	public ResponseListDTO(List<D> data, @Autowired Long totalElements) {
		this.totalElements = totalElements;
		this.data = data;
	}

	public List<D> getData() {
		return data;
	}

	public void setData(List<D> data) {
		this.data = data;
	}

	public Long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}

	

}