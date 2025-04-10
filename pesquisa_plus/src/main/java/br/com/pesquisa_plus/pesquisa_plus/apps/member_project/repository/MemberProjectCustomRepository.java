package br.com.pesquisa_plus.pesquisa_plus.apps.member_project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import br.com.pesquisa_plus.pesquisa_plus.apps.member_project.dto.MemberProjectView;
import br.com.pesquisa_plus.pesquisa_plus.apps.member_project.models.MemberProjectModel;

@Repository
public interface MemberProjectCustomRepository {

	Page<MemberProjectView> listView(Specification<MemberProjectModel> spec, Pageable pageable, boolean report);
		
}
