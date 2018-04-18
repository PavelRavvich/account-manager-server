package ru.pravvich.service;

import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pravvich.domain.Phone;
import ru.pravvich.repository.PhoneRepository;

import java.util.Collection;

@Service
public class PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    public Phone get(int id) {
        return phoneRepository.findOne(id);
    }

    public Collection<Phone> list() {
        return Lists.newArrayList(phoneRepository.findAll());
    }

    public Phone createOrUpdate(@NonNull Phone phone) {
        return phoneRepository.save(phone);
    }

    public void delete(int id) {
        phoneRepository.delete(id);
    }
}
