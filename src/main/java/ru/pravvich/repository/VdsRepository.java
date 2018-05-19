package ru.pravvich.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.pravvich.domain.Vds;

import javax.persistence.criteria.*;
import java.sql.Timestamp;

import static java.util.Objects.nonNull;
import static ru.pravvich.util.QueryValFormatter.LikeStrategy.ANY;
import static ru.pravvich.util.QueryValFormatter.toLike;

/**
 * @author Pavel Ravvich.
 */
@Repository
public interface VdsRepository extends JpaRepository<Vds, Integer>, JpaSpecificationExecutor<Vds> {

    @AllArgsConstructor
    class VdsSpecification implements Specification<Vds> {

        private final @NonNull VdsFilter filter;

        @Override
        public Predicate toPredicate(Root<Vds> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            Predicate predicate = cb.conjunction();
            if (nonNull(filter.id)) {
                predicate.getExpressions().add(cb.equal(root.get("id"), filter.id));
            }
            if (nonNull(filter.ip)) {
                predicate.getExpressions().add(cb.like(root.get("ip"), toLike(filter.ip, ANY)));
            }
            if (nonNull(filter.note)) {
                predicate.getExpressions().add(cb.like(root.get("note"), toLike(filter.note, ANY)));
            }
            if (nonNull(filter.login)) {
                predicate.getExpressions().add(cb.like(root.get("login"), toLike(filter.login, ANY)));
            }
            if (nonNull(filter.password)) {
                predicate.getExpressions().add(cb.like(root.get("login"), toLike(filter.getPassword(), ANY)));
            }
            if (nonNull(filter.from) && nonNull(filter.to)) {
                Path<Timestamp> path = root.get(filter.isActivatedDate ? "activated" : "deactivated");
                predicate.getExpressions().add(cb.between(path, filter.from, filter.to));
            }
            return predicate;
        }
    }

    @Data
    class VdsFilter {

        private @NonNull Pageable pageable;

        private Integer id;

        private String ip;

        private String note;

        private String login;

        private String password;

        private Boolean isActivatedDate;

        private Timestamp from;

        private Timestamp to;

    }
}
