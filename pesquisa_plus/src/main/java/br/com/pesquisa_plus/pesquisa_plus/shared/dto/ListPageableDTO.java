package br.com.pesquisa_plus.pesquisa_plus.shared.dto;

import java.util.List;

import br.com.pesquisa_plus.pesquisa_plus.shared.models.Filter;

public record ListPageableDTO(int page, String sortField, int sortOrder, List<Filter> filters) {
} 
