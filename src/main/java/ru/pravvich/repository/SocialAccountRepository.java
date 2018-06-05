package ru.pravvich.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pravvich.domain.SocialAccount;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static ru.pravvich.util.QueryValFormatter.toLike;

/**
 * @author Pavel Ravvich.
 */
@Repository
public interface SocialAccountRepository extends JpaRepository<SocialAccount, Integer>, JpaSpecificationExecutor<SocialAccount> {

    @Modifying
    @Transactional
    @Query("UPDATE SocialAccount acc set acc.phoneId = ?1 WHERE acc.phoneId = ?2")
    void setPhone(@NonNull Integer actual, @NonNull Integer old);

    @Modifying
    @Transactional
    @Query("UPDATE SocialAccount acc set acc.vdsId = ?1 WHERE acc.vdsId = ?2")
    void setVds(@NonNull Integer actual, @NonNull Integer old);

    @AllArgsConstructor
    class SocialAccountSpecification implements Specification<SocialAccount> {

        private final @NonNull SocialAccountFilter filter;

        @Override
        public Predicate toPredicate(Root<SocialAccount> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();
            filter.getId().ifPresent(id -> expressions.add(cb.equal(root.get("id"), id)));
            filter.getNote().ifPresent(note -> expressions.add(cb.like(root.get("note"), toLike(note))));
            filter.getStatus().ifPresent(status -> expressions.add(cb.like(root.get("status"), toLike(status))));
            filter.getSocialType().ifPresent(type -> expressions.add(cb.like(root.get("socialType"), toLike(type))));
            filter.getPassword().ifPresent(pass -> expressions.add(cb.like(root.get("password"), toLike(pass))));
            filter.getLogin().ifPresent(login -> expressions.add(cb.like(root.get("login"), toLike(login))));
            filter.getPhoneId().ifPresent(pId -> expressions.add(cb.equal(root.get("phoneId"), pId)));
            filter.getVdsId().ifPresent(vId -> expressions.add(cb.equal(root.get("vdsId"), vId)));
            if (filter.getFrom().isPresent() && filter.getTo().isPresent()) {
                expressions.add(cb.between(root.get("regDate"), filter.getFrom().get(), filter.getTo().get()));
            }
            return predicate;
        }
    }

    @Data
    class SocialAccountFilter {

        private @NonNull Pageable pageable;

        private Integer id;

        private String note;

        private String status;

        private String socialType;

        private String login;

        private String password;

        private Timestamp from;

        private Timestamp to;

        private Integer phoneId;

        private Integer vdsId;

        public Optional<Integer> getId() {
            return Optional.ofNullable(id);
        }

        public Optional<String> getNote() {
            return Optional.ofNullable(note);
        }

        public Optional<String> getStatus() {
            return Optional.ofNullable(status);
        }

        public Optional<String> getSocialType() {
            return Optional.ofNullable(socialType);
        }

        public Optional<String> getLogin() {
            return Optional.ofNullable(login);
        }

        public Optional<String> getPassword() {
            return Optional.ofNullable(password);
        }

        public Optional<Timestamp> getFrom() {
            return Optional.ofNullable(from);
        }

        public Optional<Timestamp> getTo() {
            return Optional.ofNullable(to);
        }

        public Optional<Integer> getPhoneId() {
            return Optional.ofNullable(phoneId);
        }

        public Optional<Integer> getVdsId() {
            return Optional.ofNullable(vdsId);
        }
    }
}
