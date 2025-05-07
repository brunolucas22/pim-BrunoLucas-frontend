package br.com.pesquisa_plus.pesquisa_plus.core.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenModel {

    public TokenModel(String token, String refresh, Long id, Integer type) {

        this.token = token;
        this.refresh = refresh;
        this.id = id;
        this.type = type;
        
    }

    @JsonProperty("token_access")
    private String token;

    @JsonProperty("token_refresh")
    private String refresh;

    @JsonProperty("id_user")
    private Long id;
    
    @JsonProperty("type_user")
    private Integer type;
}
