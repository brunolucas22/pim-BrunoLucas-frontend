package br.com.pesquisa_plus.pesquisa_plus.shared.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PageableDTO {

	private int first;
	@Min(value = 1,  message = "O Numero de registros deve ser maior que 1!")
	private int rows;
	private int page;
	private int pageCount;
	
	@NotNull(message = "O campo SortField é obrigatorio!")
	@NotBlank(message = "O campo SortFiel não pode ser vazio!")
	private String  sortField = "codigoEntidade";
	@NotNull(message = "O campo SortField é obrigatorio!")	
	private String  sortOrder = "1";
	private List<String> multiSortMeta;   
	private List<PageableFilterDTO> filters;
	private boolean report;
	
	public int getFirst() {
		System.out.println("chegou aqui 3");
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public List<String> getMultiSortMeta() {
		return multiSortMeta;
	}
	public void setMultiSortMeta(List<String> multiSortMeta) {
		this.multiSortMeta = multiSortMeta;
	}
	public List<PageableFilterDTO> getFilters() {
		return filters;
	}
	public void setFilters(List<PageableFilterDTO> filters) {
		this.filters = filters;
	}
	public boolean isReport() {
		return report;
	}
	public void setReport(boolean report) {
		this.report = report;
	}
	
}
