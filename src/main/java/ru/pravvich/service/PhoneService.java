package ru.pravvich.service;

import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pravvich.domain.Phone;
import ru.pravvich.repository.PhoneRepository;
import ru.pravvich.repository.SocialAccountRepository;

import java.util.Collection;

import static java.util.Objects.nonNull;

@Service
public class PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private SocialAccountRepository socialAccountRepository;

    public Phone get(int id) {
        Phone phone = phoneRepository.findOne(id);
        return nonNull(phone) ? phone : new Phone();
    }

    public Collection<Phone> list() {
        return Lists.newArrayList(phoneRepository.findAll());
    }

    public Phone saveOrUpdate(@NonNull Phone phone) {
        return phoneRepository.save(phone);
    }

    public void delete(int id) {
        socialAccountRepository.setPhone(null, id);
        phoneRepository.delete(id);
    }
}
