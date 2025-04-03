package br.com.pesquisa_plus.pesquisa_plus.apps.logging.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.pesquisa_plus.pesquisa_plus.apps.logging.dto.LogDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.logging.models.LogModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.logging.repository.LogRepository;
import br.com.pesquisa_plus.pesquisa_plus.apps.logging.repository.LogRepositoryImpl;
import br.com.pesquisa_plus.pesquisa_plus.shared.dto.RequestListDTO;
import br.com.pesquisa_plus.pesquisa_plus.shared.service.AbstractService;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class LogService extends AbstractService<LogModel, Integer>{
	
	@Autowired
	private LogRepositoryImpl logRepository;
	
	

    public void registerLog(String userName, String action,String controller, String details) {
        LogModel log = new LogModel();
        System.out.println(userName);
        log.setIdUser(userName);
        log.setAction(action);
        log.setDateTime(Timestamp.from(Instant.now()));
        log.setDetails(details);
        log.setController(controller);
        super.create(log);
    }
    
    @SuppressWarnings("unchecked")
	public Page<LogDTO> getAllView(RequestListDTO requestListDTO) {
		
		Page<LogDTO> pageLogView = logRepository.listView(requestListDTO.parserToCriteria(),
				requestListDTO.parserToPageable(), requestListDTO.getPageableDTO().isReport());
		
		return pageLogView;
	}
}