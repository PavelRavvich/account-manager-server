package ru.pravvich.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.pravvich.domain.Phone;
import ru.pravvich.repository.PhoneRepository;
import ru.pravvich.repository.PhoneRepository.PhoneFilter;
import ru.pravvich.repository.PhoneRepository.PhoneSpecification;
import ru.pravvich.repository.SocialAccountRepository;

import static java.util.Objects.nonNull;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * @author Pavel Ravvich.
 * <p>
 * Service for Phone entity.
 */
@Service
public class PhoneServiceImpl implements PhoneService {
    /**
     * Repository for Phone.
     */
    private final PhoneRepository phoneRepository;
    /**
     * Repository for SocialAccount.
     */
    private final SocialAccountRepository socialAccountRepository;

    /**
     * Default constructor.
     *
     * @param phoneRepository         injection repo for Phone.
     * @param socialAccountRepository injection for SocialAccount.
     */
    @Autowired
    public PhoneServiceImpl(@NonNull PhoneRepository phoneRepository, @NonNull SocialAccountRepository socialAccountRepository) {
        this.phoneRepository = phoneRepository;
        this.socialAccountRepository = socialAccountRepository;
    }

    /**
     * Get phone by id.
     *
     * @param id for select.
     * @return Phone with corresponding id or empty Phone.
     */
    @Override
    public Phone get(final int id) {
        Phone phone = phoneRepository.findOne(id);
        return nonNull(phone) ? phone : new Phone();
    }

    /**
     * Get Page of Phones by filter.
     *
     * @param filter for select.
     * @return page of phones.
     */
    @Override
    public Page<Phone> list(@NonNull final PhoneFilter filter) {
        Specification<Phone> specs = new PhoneSpecification(filter);
        return phoneRepository.findAll(where(specs), filter.getPageable());
    }

    /**
     * Save new Phone or update if Phone.getId() already exist.
     *
     * @param phone actual state for save to db.
     * @return created or updated Phone.
     */
    @Override
    public Phone saveOrUpdate(@NonNull final Phone phone) {
        return phoneRepository.save(phone);
    }

    /**
     * Delete Phone by id.
     *
     * @param id of Phone for delete.
     */
    @Override
    public void delete(final int id) {
        socialAccountRepository.setPhone(null, id);
        phoneRepository.delete(id);
    }
}
