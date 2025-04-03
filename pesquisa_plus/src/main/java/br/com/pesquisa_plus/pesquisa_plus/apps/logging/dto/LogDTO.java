package br.com.pesquisa_plus.pesquisa_plus.apps.logging.dto;

// Imports
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



import org.springframework.stereotype.Component;




@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component

public class LogDTO {
    private Long id;
    private String action;
    private String details;
    private Timestamp dateTime;
    private String idUser;
    private String controller;
    private Long idProject;
    private String user;
}


