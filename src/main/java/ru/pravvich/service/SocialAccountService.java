package ru.pravvich.service;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import ru.pravvich.domain.SocialAccount;
import ru.pravvich.repository.SocialAccountRepository.SocialAccountFilter;

/**
 * @author Pavel Ravvich.
 */
public interface SocialAccountService {
    Page<SocialAccount> list(@NonNull final SocialAccountFilter filter);
    SocialAccount saveOrUpdate(@NonNull final SocialAccount account);
    SocialAccount get(final int id);
    void delete(final int id);
}
