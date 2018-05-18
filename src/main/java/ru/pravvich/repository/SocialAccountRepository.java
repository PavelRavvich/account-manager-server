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
            if (nonNull(filter.id)) {
                predicate.getExpressions().add(cb.equal(root.get("id"), filter.id));
            }
            if (nonNull(filter.note)) {
                predicate.getExpressions().add(cb.like(root.get("note"), toLike(filter.note, ANY)));
            }
            if (nonNull(filter.status)) {
                predicate.getExpressions().add(cb.like(root.get("status"), toLike(filter.status, ANY)));
            }
            if (nonNull(filter.socialType)) {
                predicate.getExpressions().add(cb.like(root.get("socialType"), toLike(filter.socialType, ANY)));
            }
            if (nonNull(filter.login)) {
                predicate.getExpressions().add(cb.like(root.get("login"), toLike(filter.login, ANY)));
            }
            if (nonNull(filter.password)) {
                predicate.getExpressions().add(cb.like(root.get("password"), toLike(filter.password, ANY)));
            }
            if (nonNull(filter.from) && nonNull(filter.to)) {
                predicate.getExpressions().add(cb.between(root.get("regDate"), filter.from, filter.to));
            }
            if (nonNull(filter.phoneId)) {
                predicate.getExpressions().add(cb.equal(root.get("phoneId"), filter.phoneId));
            }
            if (nonNull(filter.vdsId)) {
                predicate.getExpressions().add(cb.equal(root.get("vdsId"), filter.vdsId));
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
    }
}
