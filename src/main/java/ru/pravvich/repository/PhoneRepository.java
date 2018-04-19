package ru.pravvich.repository;

import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.pravvich.domain.Phone;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

public interface PhoneRepository extends CrudRepository<Phone, Integer>, JpaSpecificationExecutor<Phone> {
    class Specifications {
        public static Specification<Phone> equal(@NonNull String column, @NotNull Object value) {
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(column), value);
        }

        public static Specification<Phone> contain(@NonNull String column, @NotNull String value) {
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(column), value);
        }

        public static Specification<Phone> isBetween(@NonNull String column, @NonNull Timestamp from, @NonNull Timestamp to) {
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get(column), from, to);
        }
    }
}

