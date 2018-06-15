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
import java.util.List;
import java.util.Optional;

import static ru.pravvich.util.QueryValFormatter.toLike;

/**
 * @author Pavel Ravvich.
 * <p>
 * Repository for User entity.
 */
@Repository
public interface VdsRepository extends JpaRepository<Vds, Integer>, JpaSpecificationExecutor<Vds> {

    /**
     * Specification wrapper for Vds.
     */
    @AllArgsConstructor
    class VdsSpecification implements Specification<Vds> {
        /**
         * Filter for select Vds.
         */
        private final @NonNull
        VdsFilter filter;

        /**
         * Creates a WHERE clause for a query of the referenced entity in form of a {@link Predicate} for the given
         * {@link Root} and {@link CriteriaQuery}.
         *
         * @param root specify mapping entity fields regTo filter fields.
         * @param cb   specify type of select condition (like, equal, etc)
         * @return a {@link Predicate}, may be {@literal null}.
         */
        @Override
        public Predicate toPredicate(Root<Vds> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            filter.getId().ifPresent(id -> expressions.add(cb.equal(root.get("id"), id)));
            filter.getIp().ifPresent(ip -> expressions.add(cb.like(root.get("ip"), toLike(ip))));
            filter.getNote().ifPresent(note -> expressions.add(cb.like(root.get("note"), toLike(note))));
            filter.getLogin().ifPresent(login -> expressions.add(cb.like(root.get("login"), toLike(login))));
            filter.getPassword().ifPresent(pass -> expressions.add(cb.like(root.get("login"), toLike(pass))));
            if (filter.getFrom().isPresent() && filter.getTo().isPresent() && filter.getIsActivatedDate().isPresent()) {
                Path<Timestamp> path = root.get(filter.getIsActivatedDate().get() ? "activated" : "deactivated");
                expressions.add(cb.between(path, filter.from, filter.to));
            }
            return predicate;
        }
    }

    /**
     * Filter for Vds entity.
     */
    @Data
    class VdsFilter {
        /**
         * Specify page number and page size.
         */
        private @NonNull
        Pageable pageable;
        /**
         * Vds id.
         */
        private Integer id;
        /**
         * Vds ip address.
         */
        private String ip;
        /**
         * None about Vds.
         */
        private String note;
        /**
         * Vds login.
         */
        private String login;
        /**
         * Vds password.
         */
        private String password;
        /**
         * Specify date range. Search by activated date or deactivated date.
         */
        private Boolean isActivatedDate;
        /**
         * Start range of activated date.
         */
        private Timestamp from;
        /**
         * End range of activated date.
         */
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
