package ru.pravvich.web.phone;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pravvich.config.api.RestApi;
import ru.pravvich.domain.Phone;
import ru.pravvich.domain.SocialAccount;
import ru.pravvich.service.PhoneService;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@RestApi
@RestController
@RequestMapping("/phone")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @GetMapping("/get_all")
    public List<PhoneRest> getAll() {
        return collectionToRest(phoneService.getList());
    }



    private List<PhoneRest> collectionToRest(@NonNull Collection<Phone> vdsSet) {
        return vdsSet.stream().map(this::toRest).collect(Collectors.toList());
    }

    private PhoneRest toRest(@NonNull Phone phone) {
        PhoneRest rest = new PhoneRest();
        rest.setId(phone.getId());
        rest.setNumber(phone.getNumber());
        rest.setIsActive(phone.getIsActive());
        rest.setOperatorUrl(phone.getOperatorUrl());
        rest.setOperatorType(phone.getOperatorType());
        rest.setOperatorAccLogin(phone.getOperatorAccLogin());
        rest.setOperatorAccPassword(phone.getOperatorAccPassword());
        rest.setRegDate(phone.getRegDate() != null ? phone.getRegDate().getTime() : -1);
        rest.setSocialAccountIds(
                nonNull(phone.getAccounts())
                        ? phone.getAccounts().stream().map(SocialAccount::getId).collect(Collectors.toList())
                        : Lists.newArrayList());
        return rest;
    }

    private Phone toEntity(@NonNull PhoneRest phone) {
        Phone entity = new Phone();
        entity.setId(phone.getId());
        entity.setNumber(phone.getNumber());
        entity.setIsActive(phone.getIsActive());
        entity.setOperatorUrl(phone.getOperatorUrl());
        entity.setOperatorType(phone.getOperatorType());
        entity.setOperatorAccLogin(phone.getOperatorAccLogin());
        entity.setOperatorAccPassword(entity.getOperatorAccPassword());
        entity.setRegDate(phone.getRegDate() != 0 ? new Timestamp(phone.getRegDate()) : null);
        if (nonNull(phone.getSocialAccountIds()) && !phone.getSocialAccountIds().isEmpty()) {
            Set<SocialAccount> accounts = Sets.newHashSet();
            phone.getSocialAccountIds().forEach(id -> {
                SocialAccount account = new SocialAccount();
                account.setId(id);
                accounts.add(account);
            });
            entity.setAccounts(accounts);
        }
        return entity;
    }
}
