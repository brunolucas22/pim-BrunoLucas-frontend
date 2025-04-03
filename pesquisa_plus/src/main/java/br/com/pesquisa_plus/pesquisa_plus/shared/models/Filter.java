package br.com.pesquisa_plus.pesquisa_plus.shared.models;

// Imports
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Filter {
    
    private String field;
    private String value;
    private String matchMode;
}
