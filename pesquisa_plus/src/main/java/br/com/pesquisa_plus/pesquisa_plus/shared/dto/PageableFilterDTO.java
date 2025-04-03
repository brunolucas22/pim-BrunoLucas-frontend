package br.com.pesquisa_plus.pesquisa_plus.shared.dto;

public class PageableFilterDTO {

	public PageableFilterDTO(String field, String value, String matchMode) {
		super();
		this.field = field;
		this.value = value;
		this.matchMode = matchMode;
	}
	private String field;
	private String value;
	private String matchMode;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getMatchMode() {
		return matchMode;
	}
	public void setMatchMode(String matchMode) {
		this.matchMode = matchMode;
	}

}
