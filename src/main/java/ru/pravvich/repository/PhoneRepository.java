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
 */
@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer>, JpaSpecificationExecutor<Phone> {

    @EqualsAndHashCode
    @AllArgsConstructor
    class PhoneSpecification implements Specification<Phone> {

        private final @NonNull PhoneFilter filter;

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
            if (filter.getFrom().isPresent() && filter.getTo().isPresent()) {
                expressions.add(cb.between(root.get("regDate"), filter.getFrom().get(), filter.getTo().get()));
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

        public Optional<Integer> getId() {
            return Optional.ofNullable(id);
        }

        public Optional<Timestamp> getFrom() {
            return Optional.ofNullable(from);
        }

        public Optional<Timestamp> getTo() {
            return Optional.ofNullable(to);
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

