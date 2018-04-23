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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;

import static java.util.Objects.nonNull;
import static ru.pravvich.util.QueryValFormatter.LikeStrategy.ANY;
import static ru.pravvich.util.QueryValFormatter.toLike;

@Repository
public interface SocialAccountRepository extends JpaRepository<SocialAccount, Integer>, JpaSpecificationExecutor<SocialAccount> {

    @Modifying
    @Transactional
    @Query("UPDATE SocialAccount acc set acc.phoneId = ?1 WHERE acc.phoneId = ?2")
    void setPhone(@NonNull Integer arg, @NonNull Integer old);

    @Modifying
    @Transactional
    @Query("UPDATE SocialAccount acc set acc.vdsId = ?1 WHERE acc.vdsId = ?2")
    void setVds(@NonNull Integer arg, @NonNull Integer old);

    @AllArgsConstructor
    class SocialAccountSpecification implements Specification<SocialAccount> {

        private final @NonNull SocialAccountFilter filter;

        @Override
        public Predicate toPredicate(Root<SocialAccount> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            Predicate predicate = cb.conjunction();
            if (nonNull(filter.getId())) {
                predicate.getExpressions().add(cb.equal(root.get("id"), filter.getId()));
            }
            if (nonNull(filter.getNote())) {
                predicate.getExpressions().add(cb.like(root.get("note"), toLike(filter.getNote(), ANY)));
            }
            if (nonNull(filter.getStatus())) {
                predicate.getExpressions().add(cb.like(root.get("status"), toLike(filter.getStatus(), ANY)));
            }
            if (nonNull(filter.getSocialType())) {
                predicate.getExpressions().add(cb.like(root.get("socialType"), toLike(filter.getSocialType(), ANY)));
            }
            if (nonNull(filter.getLogin())) {
                predicate.getExpressions().add(cb.like(root.get("login"), toLike(filter.getLogin(), ANY)));
            }
            if (nonNull(filter.getPassword())) {
                predicate.getExpressions().add(cb.like(root.get("password"), toLike(filter.getPassword(), ANY)));
            }
            if (nonNull(filter.getRegFrom()) && nonNull(filter.getRegTo())) {
                predicate.getExpressions().add(cb.between(root.get("regDate"), filter.getRegFrom(), filter.getRegTo()));
            }
            if (nonNull(filter.getPhoneId())) {
                predicate.getExpressions().add(cb.equal(root.get("phoneId"), filter.getPhoneId()));
            }
            if (nonNull(filter.getVdsId())) {
                predicate.getExpressions().add(cb.equal(root.get("vdsId"), filter.getVdsId()));
            }
            return predicate;
        }
    }

    @Data
    class SocialAccountFilter {

        private Pageable pageable;

        private Integer id;

        private String note;

        private String status;

        private String socialType;

        private String login;

        private String password;

        private Timestamp regFrom;

        private Timestamp regTo;

        private Integer phoneId;

        private Integer vdsId;
    }
}
