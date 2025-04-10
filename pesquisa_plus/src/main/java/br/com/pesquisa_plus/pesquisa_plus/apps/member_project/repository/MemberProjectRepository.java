package br.com.pesquisa_plus.pesquisa_plus.apps.member_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pesquisa_plus.pesquisa_plus.apps.member_project.models.MemberProjectModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.repository.BaseJpaRepository;

@Repository
public interface MemberProjectRepository extends BaseJpaRepository<MemberProjectModel, Integer> {
}