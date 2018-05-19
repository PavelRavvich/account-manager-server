package ru.pravvich.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.pravvich.domain.SocialAccount;
import ru.pravvich.repository.SocialAccountRepository;
import ru.pravvich.repository.SocialAccountRepository.SocialAccountFilter;

import static java.util.Objects.nonNull;
import static org.springframework.data.jpa.domain.Specifications.where;
import static ru.pravvich.repository.SocialAccountRepository.SocialAccountSpecification;

/**
 * @author Pavel Ravvich.
 */
@Service
public class SocialAccountServiceImpl implements SocialAccountService {

    @Autowired
    private SocialAccountRepository socialAccountRepository;

    @Override
    public Page<SocialAccount> list(@NonNull final SocialAccountFilter filter) {
        Specification<SocialAccount> specs = new SocialAccountSpecification(filter);
        return socialAccountRepository.findAll(where(specs), filter.getPageable());
    }

    @Override
    public SocialAccount get(final int id) {
        SocialAccount account = socialAccountRepository.findOne(id);
        return nonNull(account) ? account : new SocialAccount();
    }

    @Override
    public SocialAccount saveOrUpdate(@NonNull final SocialAccount account) {
        return socialAccountRepository.save(account);
    }

    @Override
    public void delete(final int id) {
        socialAccountRepository.delete(id);
    }
}
