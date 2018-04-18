package ru.pravvich.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pravvich.domain.Phone;

public interface PhoneRepository extends CrudRepository<Phone, Integer> {
}
