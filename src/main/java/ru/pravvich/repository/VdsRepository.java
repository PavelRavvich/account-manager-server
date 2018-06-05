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
import java.util.Optional;

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
            filter.getId().ifPresent(id -> predicate.getExpressions().add(cb.equal(root.get("id"), id)));
            filter.getIp().ifPresent(ip -> predicate.getExpressions().add(cb.like(root.get("ip"), toLike(ip))));
            filter.getNote().ifPresent(note -> predicate.getExpressions().add(cb.like(root.get("note"), toLike(note))));
            filter.getLogin().ifPresent(login -> predicate.getExpressions().add(cb.like(root.get("login"), toLike(login))));
            filter.getPassword().ifPresent(pass -> predicate.getExpressions().add(cb.like(root.get("login"), toLike(pass))));
            if (filter.getFrom().isPresent() && filter.getTo().isPresent() && filter.getIsActivatedDate().isPresent()) {
                Path<Timestamp> path = root.get(filter.getIsActivatedDate().get() ? "activated" : "deactivated");
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

        public Optional<Integer> getId() {
            return Optional.ofNullable(id);
        }

        public Optional<String> getIp() {
            return Optional.ofNullable(ip);
        }

        public Optional<String> getNote() {
            return Optional.ofNullable(note);
        }

        public Optional<String> getLogin() {
            return Optional.ofNullable(login);
        }

        public Optional<String> getPassword() {
            return Optional.ofNullable(password);
        }

        public Optional<Boolean> getIsActivatedDate() {
            return Optional.ofNullable(isActivatedDate);
        }

        public Optional<Timestamp> getFrom() {
            return Optional.ofNullable(from);
        }

        public Optional<Timestamp> getTo() {
            return Optional.ofNullable(to);
        }
    }
}
