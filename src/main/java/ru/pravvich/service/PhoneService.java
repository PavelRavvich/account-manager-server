package ru.pravvich.service;

import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pravvich.domain.Phone;
import ru.pravvich.domain.SocialAccount;
import ru.pravvich.repo.PhoneRepository;
import ru.pravvich.web.phone.PhoneRest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private SocialAccountService socialAccountService;

    public Phone getPhone(int id) {
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
