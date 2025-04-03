package br.com.pesquisa_plus.pesquisa_plus.shared.repository;

import org.springframework.data.jpa.domain.Specification;
import br.com.pesquisa_plus.pesquisa_plus.shared.models.Filter;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class FilterSpecification {

    public static Specification<?> withFilters(List<Filter> filters, String refactoring) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Filter filter : filters) {
                String field = filter.getField().replace(refactoring, "");
                String matchMode = filter.getMatchMode();
                Object value = filter.getValue();

                switch (matchMode.toUpperCase()) {
                    case "EQUAL":
                        predicates.add(criteriaBuilder.equal(root.get(field), value));
                        break;
                    case "NOT_EQUAL":
                        predicates.add(criteriaBuilder.notEqual(root.get(field), value));
                        break;
                    case "GREATER_THAN":
                        predicates.add(criteriaBuilder.greaterThan(root.get(field), value.toString()));
                        break;
                    case "LESS_THAN":
                        predicates.add(criteriaBuilder.lessThan(root.get(field), value.toString()));
                        break;
                    case "GREATER_THAN_EQUAL":
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(field), value.toString()));
                        break;
                    case "LESS_THAN_EQUAL":
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(field), value.toString()));
                        break;
                    case "CONTAINS":
                        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(field)), "%" + value.toString().toLowerCase() + "%"));
                        break;
                    case "CONTAINS_START":
                        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(field)), value.toString().toLowerCase() + "%"));
                        break;
                    case "CONTAINS_END":
                        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(field)), "%" + value.toString().toLowerCase()));
                        break;
                    case "IN":
                        predicates.add(root.get(field).in((List<?>) value));
                        
                        break;
                    case "NOT_IN":
                        predicates.add(criteriaBuilder.not(root.get(field).in((List<?>) value)));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown match mode: " + matchMode);
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
