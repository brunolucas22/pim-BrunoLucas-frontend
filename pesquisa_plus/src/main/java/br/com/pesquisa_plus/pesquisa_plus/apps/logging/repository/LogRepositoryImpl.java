package br.com.pesquisa_plus.pesquisa_plus.apps.logging.repository;

import java.sql.Timestamp;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import br.com.pesquisa_plus.pesquisa_plus.apps.logging.dto.LogDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.logging.models.LogModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.repository.specs.GenericSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class LogRepositoryImpl implements LogCustomRepository {
	
	private EntityManager entityManager;

	public LogRepositoryImpl(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	public Page<LogDTO> listView(Specification<LogModel> spec,
			Pageable pageable, boolean report) {
		
			return this.listTable(spec, pageable);
	}

	private Page<LogDTO> listTable(Specification<LogModel> spec, Pageable pageable) {
		
		
		 StringBuilder select = new StringBuilder();
		    StringBuilder from = new StringBuilder();
		    StringBuilder innerJoin = new StringBuilder();
		    StringBuilder where = new StringBuilder();
		    StringBuilder orderBy = new StringBuilder();
		    StringBuilder sqlCount = new StringBuilder();

		    Map<String, String> parseMap = Map.of(
		        "id", "l.id",
		        "action", "l.action",
		        "dateTime", "CAST(l.dateTime AS STRING)",
		        "details", "l.details",
		        "controller", "l.controller",
		        "idUser", "l.idUser",
		        "idProject", "CAST(l.idProject AS INTEGER)",
		        "user", "u.name"
		    );

		    Session session = (Session) entityManager.getDelegate();

		    sqlCount.append("SELECT COUNT(l) ");

		    select.append("""
		        SELECT l.id AS id, l.action AS action, l.details AS details, l.dateTime AS dateTime, l.idUser AS idUser,
		        l.controller AS controller, l.idProject AS idProject, u.name AS userName, u.email AS userEmail
		    """);

		    from.append("""
		        FROM LogModel l
		    """);

		    innerJoin.append("""
		        LEFT JOIN UserModel u ON CAST(l.idUser AS INTEGER) = u.id
		    """);
		    
		    
		    where.append(((GenericSpecification) spec).toPredicateJpql(parseMap));
		    
		    if (pageable.getSort().isSorted()) {
		        Sort.Order order = pageable.getSort().iterator().next();
		        String property = order.getProperty();
		        String direction = order.getDirection().name();

		        if (property.equals("user")) {
		            orderBy.append(" ORDER BY u.name ").append(direction); // Ordena por userName
		        } else {
		        	 orderBy.append(" ORDER BY ").append(pageable.getSort().toString().replaceAll(":", ""));
		        }
		    } else {
		    	 orderBy.append(" ORDER BY ").append(pageable.getSort().toString().replaceAll(":", ""));
		    }
		    
		    Query query = session.createQuery(sqlCount.append(from).append(innerJoin).append(where).toString(), Long.class);

		    Long totalRegistros = (Long) query.getSingleResult();

		   

		    StringBuilder sql = new StringBuilder().append(select).append(from).append(innerJoin).append(where).append(orderBy);

		   

		    query.setFirstResult(Long.valueOf(pageable.getOffset()).intValue());
		    query.setMaxResults(pageable.getPageSize());
		    
		    query = session.createQuery(sql.toString(), LogDTO.class).setTupleTransformer((tuples, aliases) -> {
		    	 LogDTO dto = new LogDTO();
			        dto.setId((Long) tuples[0]);
			        dto.setAction((String) tuples[1]);
			        dto.setDetails((String) tuples[2]);
			        dto.setDateTime((Timestamp) tuples[3]);
			        dto.setIdUser((String) tuples[4]);
			        dto.setController((String) tuples[5]);
			        dto.setIdProject((tuples[6] != null) ? Long.parseLong(tuples[6].toString()) : null);
			        dto.setUser((tuples[7] != null ? (String) tuples[7] : "") + (tuples[8] != null ? " ("+ (String) tuples[8]+")" : ""));
			        return dto;
			});

		    Page<LogDTO> page = new PageImpl<LogDTO>(query.getResultList(), pageable, totalRegistros);
			
			return page;
	}
	
	
	
}
