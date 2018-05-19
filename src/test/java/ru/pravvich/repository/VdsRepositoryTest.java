package ru.pravvich.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import ru.pravvich.config.security.H2JpaConfig;
import ru.pravvich.domain.Vds;
import ru.pravvich.repository.VdsRepository.VdsFilter;

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
public class VdsRepositoryTest {

    @Autowired
    VdsRepository vdsRepository;

    @Autowired
    EntityManager entityManager;

    private VdsFilter filter = new VdsFilter(new PageRequest(0, 1));

    @Test
    public void whenToPredicateWithFullFilterThenReturnFullPredicate() {
        String test = "test";
        filter.setId(1);
        filter.setIp(test);
        filter.setLogin(test);
        filter.setPassword(test);
        filter.setNote(test);
        filter.setIsActivatedDate(true);
        filter.setFrom(new Timestamp(System.currentTimeMillis()));
        filter.setTo(new Timestamp(System.currentTimeMillis()));

        Specification<Vds> spec = new VdsRepository.VdsSpecification(filter);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Vds> query = builder.createQuery(Vds.class);
        Root<Vds> root = query.from(Vds.class);
        Predicate predicate = spec.toPredicate(root, query, builder);
        int size = predicate.getExpressions().size();
        assertThat(size, is(6));
    }

    @Test
    public void whenToPredicateWithEmptyFilterThenReturnEmptyPredicate() {
        Specification<Vds> spec = new VdsRepository.VdsSpecification(filter);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Vds> query = builder.createQuery(Vds.class);
        Root<Vds> root = query.from(Vds.class);
        Predicate predicate = spec.toPredicate(root, query, builder);
        int size = predicate.getExpressions().size();
        assertThat(size, is(0));
    }
}