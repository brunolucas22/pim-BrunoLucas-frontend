package br.com.pesquisa_plus.pesquisa_plus.apps.member_project.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
// Imports
import org.springframework.stereotype.Service;

import br.com.pesquisa_plus.pesquisa_plus.apps.member_project.dto.MemberProjectView;
import br.com.pesquisa_plus.pesquisa_plus.apps.member_project.models.MemberProjectModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.service.AbstractService;

import br.com.pesquisa_plus.pesquisa_plus.apps.member_project.repository.MemberProjectRepository;
import br.com.pesquisa_plus.pesquisa_plus.apps.member_project.repository.MemberProjectRepositoryImpl;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.RequestListDTO;


// Annotations for the service
@Service
// Class of the access interface between business rules and bank queries
public class MemberProjectService extends AbstractService<MemberProjectModel, Integer> {

   	@Autowired
	private MemberProjectRepositoryImpl memberProjectRepositoryImpl;

    @Autowired
	private MemberProjectRepository memberProjectRepository;


    @SuppressWarnings("unchecked")
	public Page<MemberProjectView> getAllView(RequestListDTO requestListDTO) {
		
		Page<MemberProjectView> pageLogView = memberProjectRepositoryImpl.listView(requestListDTO.parserToCriteria(),
				requestListDTO.parserToPageable(), requestListDTO.getPageableDTO().isReport());
		
		return pageLogView;
	}
    
    @SuppressWarnings("unchecked")
	public Optional<MemberProjectModel> getByProjectAndUser(Long idProject, Long idUser) {
		return memberProjectRepository.findByIdProjectAndIdUser(idProject, idUser);
		
	}
}
