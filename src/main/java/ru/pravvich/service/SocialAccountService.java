package ru.pravvich.service;

import com.google.common.collect.Sets;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.pravvich.domain.SocialAccount;
import ru.pravvich.web.social.SocialAccountRest;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;
import java.util.stream.IntStream;

@Service
public class SocialAccountService {
    public SocialAccountRest getSocialAccountById(int id) {
        System.out.println(id);
        return new SocialAccountRest();
    }

    public Collection<SocialAccount> getList() {
        Set<SocialAccount> result = Sets.newHashSet();
        IntStream.range(0, 30).forEach(i -> {
            SocialAccount mockSocialAccount = getMockSocialAccount();
            mockSocialAccount.setId(i + 1);
            mockSocialAccount.setVdsId(i + 2);
            mockSocialAccount.setPhoneNumber("+1234567890");
            result.add(mockSocialAccount);
        });
        return result;
    }

    public SocialAccount create(@NonNull SocialAccount account) {
        System.out.println(account);
        return account;
    }

    public SocialAccount update(@NonNull SocialAccount account) {
        System.out.println(account);
        return account;
    }

    public void delete(int id) {
        System.out.println(id);
    }

    public SocialAccount getMockSocialAccount() {
        SocialAccount socialAccount = new SocialAccount();
        socialAccount.setId(1);
        socialAccount.setLogin("test_login");
        socialAccount.setPassword("test_pass");
        socialAccount.setNotes("some notes");
        socialAccount.setRegDate(new Timestamp(System.currentTimeMillis()));
        socialAccount.setStatus("Active");
        socialAccount.setSocialType("YouTube");
        return socialAccount;
    }
}
