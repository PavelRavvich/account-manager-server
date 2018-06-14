package ru.pravvich.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.pravvich.domain.Phone;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static ru.pravvich.util.QueryValFormatter.toLike;

/**
 * @author Pavel Ravvich.
 *
 * Repository for Phone entity.
 */
@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer>, JpaSpecificationExecutor<Phone> {
    /**
     * Specification wrapper for PhoneFilter.
     */
    @EqualsAndHashCode
    @AllArgsConstructor
    class PhoneSpecification implements Specification<Phone> {
        /**
         * Filter for select Phones.
         */
        private final @NonNull PhoneFilter filter;

        /**
         * Creates a WHERE clause for a query of the referenced entity in form of a {@link Predicate} for the given
         * {@link Root} and {@link CriteriaQuery}.
         *
         * @param root specify mapping entity fields regTo filter fields.
         * @param cb specify type of select condition (like, equal, etc)
         * @return a {@link Predicate}, may be {@literal null}.
         */
        @Override
        public Predicate toPredicate(Root<Phone> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            filter.getId().ifPresent(id -> expressions.add(cb.equal(root.get("id"), id)));
            filter.getNote().ifPresent(note -> expressions.add(cb.like(root.get("note"), toLike(note))));
            filter.getNumber().ifPresent(num -> expressions.add(cb.like(root.get("number"), toLike(num))));
            filter.getStatus().ifPresent(status -> expressions.add(cb.like(root.get("status"), toLike(status))));
            filter.getOpName().ifPresent(opName -> expressions.add(cb.like(root.get("operatorName"), toLike(opName))));
            filter.getLogin().ifPresent(login -> expressions.add(cb.like(root.get("operatorAccLogin"), toLike(login))));
            filter.getPassword().ifPresent(pass -> expressions.add(cb.like(root.get("operatorAccPassword"), toLike(pass))));
            if (filter.getRegFrom().isPresent() && filter.getRegTo().isPresent()) {
                expressions.add(cb.between(root.get("regDate"), filter.getRegFrom().get(), filter.getRegTo().get()));
            }
            return predicate;
        }
    }

    /**
     * Filter for Phone entity.
     */
    @Data
    @EqualsAndHashCode
    class PhoneFilter {
        /**
         * Specify page number and page size.
         */
        private @NonNull Pageable pageable;
        /**
         * Phone id.
         */
        private Integer id;
        /**
         * Start range of reg date.
         */
        private Timestamp regFrom;
        /**
         * End range of reg date.
         */
        private Timestamp regTo;
        /**
         * Phone number.
         */
        private String number;
        /**
         * Login in operator account.
         */
        private String login;
        /**
         * Password in operator account.
         */
        private String password;
        /**
         * Operation name.
         */
        private String opName;
        /**
         * Current status of phone number.
         */
        private String status;
        /**
         * Note about billing state.
         */
        private String note;

        public Optional<Integer> getId() {
            return Optional.ofNullable(id);
        }

        public Optional<Timestamp> getRegFrom() {
            return Optional.ofNullable(regFrom);
        }

        public Optional<Timestamp> getRegTo() {
            return Optional.ofNullable(regTo);
        }

        public Optional<String> getNumber() {
            return Optional.ofNullable(number);
        }

        public Optional<String> getLogin() {
            return Optional.ofNullable(login);
        }

        public Optional<String> getPassword() {
            return Optional.ofNullable(password);
        }

        public Optional<String> getOpName() {
            return Optional.ofNullable(opName);
        }

        public Optional<String> getStatus() {
            return Optional.ofNullable(status);
        }

        public Optional<String> getNote() {
            return Optional.ofNullable(note);
        }
    }
}

