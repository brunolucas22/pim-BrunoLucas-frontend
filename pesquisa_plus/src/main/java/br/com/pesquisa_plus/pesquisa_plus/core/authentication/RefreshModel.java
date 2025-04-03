package br.com.pesquisa_plus.pesquisa_plus.core.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class RefreshModel {

    @JsonProperty("refresh_token")
    private String refresh;
    
}
