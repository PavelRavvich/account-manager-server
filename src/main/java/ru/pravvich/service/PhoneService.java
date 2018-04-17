package ru.pravvich.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pravvich.domain.Phone;
import ru.pravvich.domain.SocialAccount;
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
    private SocialAccountService socialAccountService;

    private Phone getMockPhone() {
        Phone phone = new Phone();
        phone.setId(1);
        phone.setOperatorAccLogin("test_login");
        phone.setOperatorAccPassword("test_pass");
        phone.setOperatorUrl("test.com");
        phone.setOperatorType("TEST OPERATOR");
        phone.setNumber("+12345678090");
        phone.setIsActive("Active");
        phone.setRegDate(new Timestamp(System.currentTimeMillis()));
        SocialAccount mockSocialAccount = socialAccountService.getMockSocialAccount();
        HashSet<SocialAccount> socialAccounts = new HashSet<>();
        socialAccounts.add(mockSocialAccount);
        phone.setAccounts(socialAccounts);
        return phone;
    }

    public PhoneRest getPhone(int id) {
        System.out.println(id);
        return new PhoneRest();
    }

    public Collection<Phone> getList() {
        List<Phone> phones = new ArrayList<>();
        IntStream.range(0, 30).forEach(i -> {
            Phone mockPhone = getMockPhone();
            mockPhone.setId(i + 1);
            phones.add(mockPhone);
        });
        return phones;
    }

    public Phone create(Phone phone) {
        System.out.println(phone);
        return phone;
    }

    public Phone update(Phone phone) {
        System.out.println(phone);
        return phone;
    }

    public void delete(int id) {
        System.out.println(id);
    }
}
