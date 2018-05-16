package ru.pravvich.service;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import ru.pravvich.domain.Phone;
import ru.pravvich.repository.PhoneRepository.PhoneFilter;

public interface PhoneService {
    Phone get(final int id);
    Page<Phone> list(@NonNull final PhoneFilter filter);
    Phone saveOrUpdate(@NonNull final Phone phone);
    void delete(final int id);
}
