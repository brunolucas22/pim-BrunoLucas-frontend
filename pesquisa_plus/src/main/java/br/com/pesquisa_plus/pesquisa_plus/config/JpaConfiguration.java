package br.com.pesquisa_plus.pesquisa_plus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.com.pesquisa_plus.pesquisa_plus.shared.repository.BaseJpaRepositoryImpl;


@Configuration
@EnableJpaRepositories(
      value = {
    		  "br.com.pesquisa_plus.pesquisa_plus"
//    		  "br.gov.ce.sgic.contractscommons.repository"
    		  },

      repositoryBaseClass = BaseJpaRepositoryImpl.class
  )
public class JpaConfiguration {

}
