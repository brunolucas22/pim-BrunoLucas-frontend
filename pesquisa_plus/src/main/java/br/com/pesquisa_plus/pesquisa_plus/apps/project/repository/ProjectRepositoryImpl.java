package br.com.pesquisa_plus.pesquisa_plus.apps.project.repository;



import br.com.pesquisa_plus.pesquisa_plus.apps.project.dto.ProjectDTO;
import br.com.pesquisa_plus.pesquisa_plus.apps.project.models.ProjectModel;
import br.com.pesquisa_plus.pesquisa_plus.apps.user.models.UserModel;
import br.com.pesquisa_plus.pesquisa_plus.shared.repository.specs.GenericSpecification;
import br.com.pesquisa_plus.pesquisa_plus.utils.AuthUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import br.com.pesquisa_plus.pesquisa_plus.apps.project.dto.ProjectDTO;
import br.com.pesquisa_plus.pesquisa_plus.utils.AuthUtil;



    @Repository
    public class ProjectRepositoryImpl implements ProjectCustomRepository {

        @PersistenceContext
        private EntityManager entityManager;

        @Autowired
        static AuthUtil authUtil; // Removido 'static'

        @Override
        public Page<ProjectDTO> listView(Specification<ProjectModel> spec, Pageable pageable, boolean isReport) {
            UserModel usuarioLogado = authUtil.getUsuarioLogado();
            boolean isAdmin = (usuarioLogado != null && usuarioLogado.getTypeUser() == 1);

            StringBuilder select = new StringBuilder();
            StringBuilder from = new StringBuilder();
            StringBuilder innerJoin = new StringBuilder();
            StringBuilder where = new StringBuilder();
            StringBuilder orderBy = new StringBuilder();
            StringBuilder sqlCount = new StringBuilder();

            Map<String, String> parseMap = Map.of(
                    "id", "p.id",
                    "nameProject", "p.nameProject",
                    "startDateProject", "p.startDateProject",
                    "expectedFinalDateProject", "p.expectedFinalDateProject",
                    "statusProject", "p.statusProject"

            );

            Session session = (Session) entityManager.getDelegate();

            sqlCount.append("SELECT COUNT(p) ");

            select.append("""
		        SELECT p.id AS id, p.nameProject AS nameProject, p.expectedFinalDateProject AS expectedFinalDateProject,
                 p.startDateProject AS startDateProject, p.statusProject AS statusProject, p.valueProject AS valueProject,
                 p.finalDateProject AS finalDateProject, p.durationProject AS durationProject, p.descriptionProject AS descriptionProject,
                 p.statusManualProject AS statusManualProject
             
		    """);

            from.append("""
		        FROM ProjectStatusModel p
		    """);
            if(!isAdmin) {

                innerJoin.append("""
                   INNER JOIN MemberProjectModel mp ON mp.idProject = p.id
                  
                """);

                where.append(((GenericSpecification) spec).toPredicateJpql(parseMap) + "AND mp.idUser = " + usuarioLogado.getId());
            }else{
                where.append(((GenericSpecification) spec).toPredicateJpql(parseMap));
            }



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



            query = session.createQuery(sql.toString(), ProjectDTO.class).setTupleTransformer((tuples, aliases) -> {
                ProjectDTO dto = new ProjectDTO();
                dto.setId((Long) tuples[0]);
                dto.setNameProject((String) tuples[1]);
                dto.setExpectedFinalDateProject((LocalDate) tuples[2]);
                dto.setStartDateProject((LocalDate) tuples[3]);
                dto.setStatusProject((String) tuples[4]);
                dto.setValueProject((BigDecimal) tuples[5]);
                dto.setFinalDateProject((LocalDate) tuples[6]);
                dto.setDurationProject((Integer) tuples[7]);
                dto.setDescriptionProject((String) tuples[8]);
                dto.setStatusManualProject((String) tuples[9]);



                return dto;
            });

            query.setFirstResult(Long.valueOf(pageable.getOffset()).intValue());
            query.setMaxResults(pageable.getPageSize());

            Page<ProjectDTO> page = new PageImpl<ProjectDTO>(query.getResultList(), pageable, totalRegistros);

            return page;
        }
    }