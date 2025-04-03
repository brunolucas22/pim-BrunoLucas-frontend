package br.com.pesquisa_plus.pesquisa_plus.shared.enums;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public enum MatchMode {
    EQUAL {
        @Override
        public <T> void addPredicate(List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Root<T> root, String field, Object value) {
            predicates.add(criteriaBuilder.equal(root.get(field), value));
        }
    },
    NOT_EQUAL {
        @Override
        public <T> void addPredicate(List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Root<T> root, String field, Object value) {
            predicates.add(criteriaBuilder.notEqual(root.get(field), value));
        }
    },
    GREATER_THAN {
        @Override
        public <T> void addPredicate(List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Root<T> root, String field, Object value) {
            predicates.add(criteriaBuilder.greaterThan(root.get(field), value.toString()));
        }
    },
    LESS_THAN {
        @Override
        public <T> void addPredicate(List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Root<T> root, String field, Object value) {
            predicates.add(criteriaBuilder.lessThan(root.get(field), value.toString()));
        }
    },
    GREATER_THAN_EQUAL {
        @Override
        public <T> void addPredicate(List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Root<T> root, String field, Object value) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(field), value.toString()));
        }
    },
    LESS_THAN_EQUAL {
        @Override
        public <T> void addPredicate(List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Root<T> root, String field, Object value) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(field), value.toString()));
        }
    },
    CONTAINS {
        @Override
        public <T> void addPredicate(List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Root<T> root, String field, Object value) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(field)), "%" + value.toString().toLowerCase() + "%"));
        }
    },
    CONTAINS_START {
        @Override
        public <T> void addPredicate(List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Root<T> root, String field, Object value) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(field)), value.toString().toLowerCase() + "%"));
        }
    },
    CONTAINS_END {
        @Override
        public <T> void addPredicate(List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Root<T> root, String field, Object value) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(field)), "%" + value.toString().toLowerCase()));
        }
    },
    IN {
        @Override
        public <T> void addPredicate(List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Root<T> root, String field, Object value) {
            if (value instanceof List<?>) {
                predicates.add(root.get(field).in((List<?>) value));
            } else {
                throw new IllegalArgumentException("Value for IN match mode must be a List.");
            }
        }
    },
    NOT_IN {
        @Override
        public <T> void addPredicate(List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Root<T> root, String field, Object value) {
            if (value instanceof List<?>) {
                predicates.add(criteriaBuilder.not(root.get(field).in((List<?>) value)));
            } else {
                throw new IllegalArgumentException("Value for NOT_IN match mode must be a List.");
            }
        }
    };

    public abstract <T> void addPredicate(List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Root<T> root, String field, Object value);
}
