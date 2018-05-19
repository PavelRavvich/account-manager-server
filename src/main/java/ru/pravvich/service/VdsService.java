package ru.pravvich.service;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import ru.pravvich.domain.Vds;
import ru.pravvich.repository.VdsRepository.VdsFilter;

/**
 * @author Pavel Ravvich.
 */
public interface VdsService {
    Page<Vds> list(@NonNull final VdsFilter filter);
    Vds saveOrUpdate(@NonNull final Vds vds);
    void delete(final int id);
    Vds get(final int id);
}
