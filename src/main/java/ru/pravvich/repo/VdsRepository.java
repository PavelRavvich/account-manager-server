package ru.pravvich.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.pravvich.domain.Vds;

@Repository
public interface VdsRepository extends CrudRepository<Vds, Integer> {
}
