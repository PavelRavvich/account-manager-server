package ru.pravvich.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pravvich.config.security.H2JpaConfig;
import ru.pravvich.domain.Phone;
import ru.pravvich.repository.PhoneRepository.PhoneFilter;

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
public class PhoneRepositoryTest {

    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    EntityManager entityManager;

    private PhoneFilter filter = new PhoneFilter(new PageRequest(0, 1));

    @Test
    public void whenToPredicateWithFullFilterThenReturnFullPredicate() {
        String test = "test";
        filter.setId(1);
        filter.setNote(test);
        filter.setNumber(test);
        filter.setStatus(test);
        filter.setOpName(test);
        filter.setLogin(test);
        filter.setPassword(test);
        filter.setFrom(new Timestamp(System.currentTimeMillis()));
        filter.setTo(new Timestamp(System.currentTimeMillis()));

        Specification<Phone> spec = new PhoneRepository.PhoneSpecification(filter);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Phone> query = builder.createQuery(Phone.class);
        Root<Phone> root = query.from(Phone.class);
        Predicate predicate = spec.toPredicate(root, query, builder);
        int size = predicate.getExpressions().size();
        assertThat(size, is(8));
    }

    @Test
    public void whenToPredicateWithEmptyFilterThenReturnEmptyPredicate() {
        Specification<Phone> spec = new PhoneRepository.PhoneSpecification(filter);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Phone> query = builder.createQuery(Phone.class);
        Root<Phone> root = query.from(Phone.class);
        Predicate predicate = spec.toPredicate(root, query, builder);
        int size = predicate.getExpressions().size();
        assertThat(size, is(0));
    }
}