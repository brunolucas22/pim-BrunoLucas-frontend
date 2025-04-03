package br.com.pesquisa_plus.pesquisa_plus.apps.logging.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pesquisa_plus.pesquisa_plus.apps.logging.models.LogModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.repository.BaseJpaRepository;

@Repository
public interface LogRepository extends BaseJpaRepository<LogModel, Integer> {
}