package ru.pravvich.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.pravvich.domain.Phone;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;

import static java.util.Objects.nonNull;
import static ru.pravvich.util.QueryValFormatter.LikeStrategy.ANY;
import static ru.pravvich.util.QueryValFormatter.toLike;

public interface PhoneRepository extends JpaRepository<Phone, Integer>, JpaSpecificationExecutor<Phone> {

    @EqualsAndHashCode
    @AllArgsConstructor
    class PhoneSpecification implements Specification<Phone> {

        private final @NonNull PhoneFilter filter;

        @Override
        public Predicate toPredicate(Root<Phone> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            Predicate predicate = cb.conjunction();
            if (nonNull(filter.id)) {
                predicate.getExpressions().add(cb.equal(root.get("id"), filter.id));
            }
            if (nonNull(filter.note)) {
                predicate.getExpressions().add(cb.like(root.get("note"), toLike(filter.note, ANY)));
            }
            if (nonNull(filter.number)) {
                predicate.getExpressions().add(cb.like(root.get("number"), toLike(filter.number, ANY)));
            }
            if (nonNull(filter.status)) {
                predicate.getExpressions().add(cb.like(root.get("status"), toLike(filter.status, ANY)));
            }
            if (nonNull(filter.opName)) {
                predicate.getExpressions().add(cb.like(root.get("operatorName"), toLike(filter.opName, ANY)));
            }
            if (nonNull(filter.login)) {
                predicate.getExpressions().add(cb.like(root.get("operatorAccLogin"), toLike(filter.login, ANY)));
            }
            if (nonNull(filter.password)) {
                predicate.getExpressions().add(cb.like(root.get("operatorAccPassword"), toLike(filter.password, ANY)));
            }
            if (nonNull(filter.from) && nonNull(filter.to)) {
                predicate.getExpressions().add(cb.between(root.get("regDate"), filter.from, filter.to));
            }
            return predicate;
        }
    }

    @Data
    @EqualsAndHashCode
    class PhoneFilter {

        private @NonNull Pageable pageable;

        private Integer id;

        private Timestamp from;

        private Timestamp to;

        private String number;

        private String login;

        private String password;

        private String opName;

        private String status;

        private String note;

    }
}

