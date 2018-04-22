package ru.pravvich.repository;

import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.pravvich.domain.Phone;
import ru.pravvich.service.PhoneService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static java.util.Objects.nonNull;
import static ru.pravvich.util.QueryValFormatter.LikeStrategy.ANY;
import static ru.pravvich.util.QueryValFormatter.toLike;

public interface PhoneRepository extends CrudRepository<Phone, Integer>, JpaRepository<Phone, Integer>, JpaSpecificationExecutor<Phone> {
    class PhoneSpecification implements Specification<Phone> {

        private final @NonNull PhoneService.PhoneFilter filter;

        public PhoneSpecification(@NonNull PhoneService.PhoneFilter filter) {
            this.filter = filter;
        }

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
}

