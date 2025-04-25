package br.com.pesquisa_plus.pesquisa_plus.apps.member_project.repository;


import java.math.BigDecimal;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import br.com.pesquisa_plus.pesquisa_plus.apps.member_project.dto.MemberProjectView;
import br.com.pesquisa_plus.pesquisa_plus.apps.member_project.models.MemberProjectModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.repository.specs.GenericSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class MemberProjectRepositoryImpl implements MemberProjectCustomRepository {
	
	private EntityManager entityManager;

	public MemberProjectRepositoryImpl(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	public Page<MemberProjectView> listView(Specification<MemberProjectModel> spec,
			Pageable pageable, boolean report) {
		
			return this.listTable(spec, pageable);
	}

	private Page<MemberProjectView> listTable(Specification<MemberProjectModel> spec, Pageable pageable) {
		
		
		 	StringBuilder select = new StringBuilder();
		    StringBuilder from = new StringBuilder();
		    StringBuilder innerJoin = new StringBuilder();
		    StringBuilder where = new StringBuilder();
		    StringBuilder orderBy = new StringBuilder();
		    StringBuilder sqlCount = new StringBuilder();

		    Map<String, String> parseMap = Map.of(
		        "id", "m.id",
		        "roleMemberProject", "m.roleMemberProject",
		        "scholarshipMemberProject", "m.scholarshipMemberProject",
		        "permissionsMemberProject", "m.permissionsMemberProject",
		        "idSupervisor", "m.idSupervisor",
		        "user", "u.nameUser"
		    );

		    Session session = (Session) entityManager.getDelegate();

		    sqlCount.append("SELECT COUNT(m) ");

		    select.append("""
		        SELECT m.id AS id, m.roleMemberProject AS roleMemberProject, m.permissionsMemberProject AS permissionsMemberProject, m.scholarshipMemberProject AS scholarshipMemberProject, m.idSupervisor AS idSupervisor,
		        u.nameUser AS userName, u.photoUser AS photoUser, u.id AS idUser
		    """);

		    from.append("""
		        FROM MemberProjectModel m
		    """);

		    innerJoin.append("""
		        LEFT JOIN UserModel u ON m.idUser = u.id
		    """);
		    
		    
		    where.append(((GenericSpecification) spec).toPredicateJpql(parseMap));
		    
		    if (pageable.getSort().isSorted()) {
		        Sort.Order order = pageable.getSort().iterator().next();
		        String property = order.getProperty();
		        String direction = order.getDirection().name();

		        if (property.equals("user")) {
		            orderBy.append(" ORDER BY u.nameUser ").append(direction); 
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
		    
		    query = session.createQuery(sql.toString(), MemberProjectView.class).setTupleTransformer((tuples, aliases) -> {
		    	 MemberProjectView dto = new MemberProjectView();
			        dto.setId((Long) tuples[0]);
			        dto.setRoleMemberProject((String) tuples[1]);
			        dto.setPermissionsMemberProject((Integer[]) tuples[2]); 
			        dto.setScholarshipMemberProject((BigDecimal) tuples[3]);
			        dto.setIdSupervisor((Long) tuples[4]);
			        dto.setUser(tuples[5] != null ? (String) tuples[5] : "");
			        dto.setHavePhoto(tuples[6] != null ? true : false);			        
			        dto.setIdUser(tuples[7] != null ? (Long) tuples[7] : null);
			        return dto;
			});

		    Page<MemberProjectView> page = new PageImpl<MemberProjectView>(query.getResultList(), pageable, totalRegistros);
			
			return page;
	}
	
	
	
}
