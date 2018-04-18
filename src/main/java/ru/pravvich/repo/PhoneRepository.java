package ru.pravvich.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.pravvich.domain.Phone;

public interface PhoneRepository extends CrudRepository<Phone, Integer> {

    @Modifying
    @Transactional()
    @Query("update Phone p set p.note = ?2 where id = ?1")
    Phone updatePhone(Integer id, String note);
}
