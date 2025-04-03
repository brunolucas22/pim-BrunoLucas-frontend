package br.com.pesquisa_plus.pesquisa_plus.core.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenModel {

    public TokenModel(String token, String refresh) {

        this.token = token;
        this.refresh = refresh;
        
    }

    @JsonProperty("token_access")
    private String token;

    @JsonProperty("token_refresh")
    private String refresh;
    
}
