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
 */
@Service
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private SocialAccountRepository socialAccountRepository;

    @Override
    public Phone get(final int id) {
        Phone phone = phoneRepository.findOne(id);
        return nonNull(phone) ? phone : new Phone();
    }

    @Override
    public Page<Phone> list(@NonNull final PhoneFilter filter) {
        Specification<Phone> specs = new PhoneSpecification(filter);
        return phoneRepository.findAll(where(specs), filter.getPageable());
    }

    @Override
    public Phone saveOrUpdate(@NonNull final Phone phone) {
        return phoneRepository.save(phone);
    }

    @Override
    public void delete(final int id) {
        socialAccountRepository.setPhone(null, id);
        phoneRepository.delete(id);
    }
}
