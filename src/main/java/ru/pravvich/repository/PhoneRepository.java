package ru.pravvich.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
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

    @AllArgsConstructor
    class PhoneSpecification implements Specification<Phone> {

        private final @NonNull PhoneFilter filter;

        @Override
        public Predicate toPredicate(Root<Phone> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            Predicate predicate = cb.conjunction();
            if (nonNull(filter.getId())) {
                predicate.getExpressions().add(cb.equal(root.get("id"), filter.getId()));
            }
            if (nonNull(filter.getNote())) {
                predicate.getExpressions().add(cb.like(root.get("note"), toLike(filter.getNote(), ANY)));
            }
            if (nonNull(filter.getNumber())) {
                predicate.getExpressions().add(cb.like(root.get("number"), toLike(filter.getNumber(), ANY)));
            }
            if (nonNull(filter.getStatus())) {
                predicate.getExpressions().add(cb.like(root.get("status"), toLike(filter.getStatus(), ANY)));
            }
            if (nonNull(filter.getOpName())) {
                predicate.getExpressions().add(cb.like(root.get("operatorName"), toLike(filter.getOpName(), ANY)));
            }
            if (nonNull(filter.getOpLogin())) {
                predicate.getExpressions().add(cb.like(root.get("operatorAccLogin"), toLike(filter.getOpLogin(), ANY)));
            }
            if (nonNull(filter.getOpPassword())) {
                predicate.getExpressions().add(cb.like(root.get("operatorAccPassword"), toLike(filter.getOpPassword(), ANY)));
            }
            if (nonNull(filter.getRegFrom()) && nonNull(filter.getRegTo())) {
                predicate.getExpressions().add(cb.between(root.get("regDate"), filter.getRegFrom(), filter.getRegTo()));
            }
            return predicate;
        }
    }

    @Data
    class PhoneFilter {

        private Pageable pageable;

        private Integer id;

        private Timestamp regFrom;

        private Timestamp regTo;

        private String number;

        private String opLogin;

        private String opPassword;

        private String opName;

        private String status;

        private String note;

    }
}

