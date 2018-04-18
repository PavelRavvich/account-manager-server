package ru.pravvich.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pravvich.domain.SocialAccount;
import ru.pravvich.repository.PhoneRepository;
import ru.pravvich.repository.SocialAccountRepository;
import ru.pravvich.web.social.SocialAccountRest;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.Objects.nonNull;

@Service
public class SocialAccountService {

    @Autowired
    private SocialAccountRepository socialAccountRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    public Collection<SocialAccount> list() {
        return Lists.newArrayList(socialAccountRepository.findAll());
    }

    public SocialAccount get(int id) {
        SocialAccount account = socialAccountRepository.findOne(id);
        return nonNull(account) ? account : new SocialAccount();
    }

    public SocialAccount saveOrUpdate(@NonNull SocialAccount account) {
        return socialAccountRepository.save(account);
    }

    public void delete(int id) {
        socialAccountRepository.delete(id);
    }
}
