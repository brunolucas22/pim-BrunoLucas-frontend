package br.com.pesquisa_plus.pesquisa_plus.shared.repository.specs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import br.com.pesquisa_plus.pesquisa_plus.utils.StringUtilLibrary;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class GenericSpecification<T> implements Specification<T> {

    private List<SearchCriteria> list;

    public GenericSpecification() {
        this.list = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        //create a new predicate listT
        List<Predicate> predicates = new ArrayList<>();

        //add add criteria to predicates
        for (SearchCriteria criteria : list) {
            if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
                predicates.add(builder.greaterThan(
                        root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
                predicates.add(builder.lessThan(
                        root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
                predicates.add(builder.greaterThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
                predicates.add(builder.lessThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
                predicates.add(builder.notEqual(
                        root.get(criteria.getKey()), criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                predicates.add(builder.equal(
                        root.get(criteria.getKey()), criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {            	       	          	            	
                predicates.add(builder.like(
                		builder.function("unaccent",String.class,  builder.upper(root.get(criteria.getKey()))),
                        "%" + StringUtilLibrary.removerAcentos(criteria.getValue().toString()).toUpperCase().replaceAll(" ", "%") + "%"));
            } else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getKey())),
                        criteria.getValue().toString().toLowerCase() + "%"));
            } else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getKey())),
                        "%" + criteria.getValue().toString().toLowerCase()));
            } else if (criteria.getOperation().equals(SearchOperation.IN)) {
                predicates.add(builder.in(root.get(criteria.getKey())).value(criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
                predicates.add(builder.not(root.get(criteria.getKey())).in(criteria.getValue()));
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
	
	 public String toPredicateJpql(Map<String, String> parseMap) {

	        //create a new predicate listT
	       // List<Predicate> predicates = new ArrayList<>();
	        StringBuilder predicates =  new StringBuilder();
	        //add add criteria to predicates
	        for (SearchCriteria criteria : list) {
	            if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");	            	
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();
	            	predicates.append(key);
	            	predicates.append(" > ");
	            	
	            	if(StringUtils.isNumeric(criteria.getValue().toString()))
	            		predicates.append(criteria.getValue().toString());
	            	else	
	            		predicates.append(" '" + criteria.getValue().toString() + "' ");
	            	
	            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
	            	
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();
	            	predicates.append(key);
	            	predicates.append(" < ");
	            	
	            	if(StringUtils.isNumeric(criteria.getValue().toString()))
	            		predicates.append(criteria.getValue().toString());
	            	else	
	            		predicates.append(" '" + criteria.getValue().toString() + "' ");

	            } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
	            	predicates.append(key);
	            	predicates.append(" >= ");
	            	
	            	if(StringUtils.isNumeric(criteria.getValue().toString()))
	            		predicates.append(criteria.getValue().toString());
	            	else	
	            		predicates.append(" '" + criteria.getValue().toString() + "' ");

	            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
	            	predicates.append(key);
	            	predicates.append(" <= ");
	            	
	            	if(StringUtils.isNumeric(criteria.getValue().toString()))
	            		predicates.append(criteria.getValue().toString());
	            	else	
	            		predicates.append(" '" + criteria.getValue().toString() + "' ");
	            	
	            } else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
	            	predicates.append(key);
	            	predicates.append(" != ");
	            	
	            	if(StringUtils.isNumeric(criteria.getValue().toString()))
	            		predicates.append(criteria.getValue().toString());
	            	else	
	            		predicates.append(" '" + criteria.getValue().toString() + "' ");	            		                

	            } else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
	            	predicates.append(key);
	            	predicates.append(" = ");
	            	
	            	if(StringUtils.isNumeric(criteria.getValue().toString()))
	            		predicates.append(criteria.getValue().toString());
	            	else	
	            		predicates.append(" '" + criteria.getValue().toString() + "' ");	            		                

	            } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();
	            	predicates.append("FUNCTION('unaccent', UPPER(" + key + "))");
	            	String value = StringUtilLibrary.removerAcentos(criteria.getValue().toString()).toUpperCase().replaceAll(" ", "%");
	            	predicates.append(" like  ");
	            	predicates.append("'%" + value + "%'  ");
	            } else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
	            	predicates.append(key);
	            	predicates.append(" like ' ");
	            	predicates.append(criteria.getValue().toString().toUpperCase() + "%' ");
	            	
	            } else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
	            	predicates.append(key);
	            	predicates.append(" like ' %");
	            	predicates.append(criteria.getValue().toString().toUpperCase() + "' ");

	            } else if (criteria.getOperation().equals(SearchOperation.IN)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
	            	predicates.append(key);
	            	predicates.append(" in( ");
	            	predicates.append(criteria.getValue() + ")");	            
	            } else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
	            	predicates.append(key);
	            	predicates.append(" not in( ");
	            	predicates.append(criteria.getValue() + ")");
	            }else if (criteria.getOperation().equals(SearchOperation.IS_NULL)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
	            	predicates.append(key);
	            	predicates.append(" IS NULL ");
	            	
	            }else if (criteria.getOperation().equals(SearchOperation.BETWEEN)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
	            	predicates.append(key);
	            	predicates.append(" BETWEEN '");
	            	predicates.append(criteria.getValue().toString().replace("#", "' AND '") + "'");
	            }else if (criteria.getOperation().equals(SearchOperation.NOT_BETWEEN)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
	            	predicates.append(key);
	            	predicates.append(" NOT BETWEEN '");
	            	predicates.append(criteria.getValue().toString().replace("#", "' AND '") + "'");
	            }
	           
	        }

	        return predicates.toString();
	    }
	 
	 public String toPredicateNativeQuery(Map<String, String> parseMap) {

	        //create a new predicate listT
	       // List<Predicate> predicates = new ArrayList<>();
	        StringBuilder predicates =  new StringBuilder();
	        //add add criteria to predicates
	        for (SearchCriteria criteria : list) {
	            if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");	            	
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();
	            	predicates.append(key);
	            	predicates.append(" > ");
	            	predicates.append(criteria.getValue().toString());	            		                
	            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
	            	
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();
	            	predicates.append(key);
	            	predicates.append(" < ");
	            	predicates.append(criteria.getValue().toString());	  

	            } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
	            	predicates.append(key);
	            	predicates.append(" >= ");
	            	predicates.append(criteria.getValue().toString());	            		                

	            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
	            	predicates.append(key);
	            	predicates.append(" <= ");
	            	predicates.append(criteria.getValue().toString());	            		                
	            } else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
	            	predicates.append(key);
	            	predicates.append(" != ");
	            	predicates.append(criteria.getValue().toString());	            		                

	            } else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
	            	predicates.append(key);
	            	predicates.append(" = ");
	            	predicates.append(criteria.getValue().toString());	            		                

	            } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();
	            	predicates.append("unaccent(UPPER(" + key + "))");
	            	String value = StringUtilLibrary.removerAcentos(criteria.getValue().toString()).toUpperCase().replaceAll(" ", "%");
	            	predicates.append(" like  ");
	            	predicates.append("'%" + value + "%'  ");
	            } else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
	            	predicates.append(key);
	            	predicates.append(" like ' ");
	            	predicates.append(criteria.getValue().toString().toUpperCase() + "%' ");
	            	
	            } else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
	            	predicates.append(key);
	            	predicates.append(" like ' %");
	            	predicates.append(criteria.getValue().toString().toUpperCase() + "' ");

	            } else if (criteria.getOperation().equals(SearchOperation.IN)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
	            	predicates.append(key);
	            	predicates.append(" in( ");
	            	predicates.append(criteria.getValue() + ")");	            
	            } else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
	            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
	            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
	            	predicates.append(key);
	            	predicates.append(" not in( ");
	            	predicates.append(criteria.getValue() + ")");
	            }
	        }

	        return predicates.toString();
	    }

	public StringBuilder toPredicateJpql(Map<String, String> parseMap, StringBuilder predicates) {
		 //add add criteria to predicates
        for (SearchCriteria criteria : list) {
            if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");	            	
            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();
            	predicates.append(key);
            	predicates.append(" > ");
            	
            	if(StringUtils.isNumeric(criteria.getValue().toString()))
            		predicates.append(criteria.getValue().toString());
            	else	
            		predicates.append(" '" + criteria.getValue().toString() + "' ");
            	
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
            	
            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();
            	predicates.append(key);
            	predicates.append(" < ");
            	
            	if(StringUtils.isNumeric(criteria.getValue().toString()))
            		predicates.append(criteria.getValue().toString());
            	else	
            		predicates.append(" '" + criteria.getValue().toString() + "' ");

            } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
            	predicates.append(key);
            	predicates.append(" >= ");
            	
            	if(StringUtils.isNumeric(criteria.getValue().toString()))
            		predicates.append(criteria.getValue().toString());
            	else	
            		predicates.append(" '" + criteria.getValue().toString() + "' ");

            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
            	predicates.append(key);
            	predicates.append(" <= ");
            	
            	if(StringUtils.isNumeric(criteria.getValue().toString()))
            		predicates.append(criteria.getValue().toString());
            	else	
            		predicates.append(" '" + criteria.getValue().toString() + "' ");
            	
            } else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
            	predicates.append(key);
            	predicates.append(" != ");
            	
            	if(StringUtils.isNumeric(criteria.getValue().toString()))
            		predicates.append(criteria.getValue().toString());
            	else	
            		predicates.append(" '" + criteria.getValue().toString() + "' ");	            		                

            } else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
            	predicates.append(key);
            	predicates.append(" = ");
            	
            	if(StringUtils.isNumeric(criteria.getValue().toString()))
            		predicates.append(criteria.getValue().toString());
            	else	
            		predicates.append(" '" + criteria.getValue().toString() + "' ");	            		                

            } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();
            	predicates.append("FUNCTION('unaccent', UPPER(" + key + "))");
            	String value = StringUtilLibrary.removerAcentos(criteria.getValue().toString()).toUpperCase().replaceAll(" ", "%");
            	predicates.append(" like  ");
            	predicates.append("'%" + value + "%'  ");
            } else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
            	predicates.append(key);
            	predicates.append(" like ' ");
            	predicates.append(criteria.getValue().toString().toUpperCase() + "%' ");
            	
            } else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
            	predicates.append(key);
            	predicates.append(" like ' %");
            	predicates.append(criteria.getValue().toString().toUpperCase() + "' ");

            } else if (criteria.getOperation().equals(SearchOperation.IN)) {
            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
            	predicates.append(key);
            	predicates.append(" in( ");
            	predicates.append(criteria.getValue() + ")");	            
            } else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
            	predicates.append(predicates.indexOf(" where ") != -1 ? " and " : " where ");
            	String key = parseMap.containsKey(criteria.getKey()) ? parseMap.get(criteria.getKey()) : criteria.getKey();	            	
            	predicates.append(key);
            	predicates.append(" not in( ");
            	predicates.append(criteria.getValue() + ")");
            }
        }

        return predicates;
	}
	 
	
}