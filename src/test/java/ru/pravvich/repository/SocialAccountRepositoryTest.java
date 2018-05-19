package ru.pravvich.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pravvich.config.security.H2JpaConfig;
import ru.pravvich.domain.SocialAccount;
import ru.pravvich.repository.SocialAccountRepository.SocialAccountFilter;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Pavel Ravvich.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = H2JpaConfig.class)
public class SocialAccountRepositoryTest {

    @Autowired
    SocialAccountRepository socialAccountRepository;

    @Autowired
    EntityManager entityManager;

    private SocialAccountFilter filter = new SocialAccountFilter(new PageRequest(0, 1));

    @Test
    public void whenToPredicateWithFullFilterThenReturnFullPredicate() {
        String test = "test";
        filter.setId(1);
        filter.setVdsId(1);
        filter.setPhoneId(1);
        filter.setSocialType(test);
        filter.setLogin(test);
        filter.setPassword(test);
        filter.setNote(test);
        filter.setStatus(test);
        filter.setFrom(new Timestamp(System.currentTimeMillis()));
        filter.setTo(new Timestamp(System.currentTimeMillis()));

        Specification<SocialAccount> spec = new SocialAccountRepository.SocialAccountSpecification(filter);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SocialAccount> query = builder.createQuery(SocialAccount.class);
        Root<SocialAccount> root = query.from(SocialAccount.class);
        Predicate predicate = spec.toPredicate(root, query, builder);
        int size = predicate.getExpressions().size();
        assertThat(size, is(9));
    }

    @Test
    public void whenToPredicateWithEmptyFilterThenReturnEmptyPredicate() {
        Specification<SocialAccount> spec = new SocialAccountRepository.SocialAccountSpecification(filter);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SocialAccount> query = builder.createQuery(SocialAccount.class);
        Root<SocialAccount> root = query.from(SocialAccount.class);
        Predicate predicate = spec.toPredicate(root, query, builder);
        int size = predicate.getExpressions().size();
        assertThat(size, is(0));
    }
}