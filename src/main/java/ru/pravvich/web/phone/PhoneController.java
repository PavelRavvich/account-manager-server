package ru.pravvich.web.phone;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/list")
    public List<PhoneRest> list() {
        return collectionToRest(phoneService.list());
    }

    @GetMapping("/get")
    public PhoneRest get(@RequestParam(name = "id") int id) {
        return toRest(phoneService.getPhone(id));
    }

    @PostMapping("/create")
    public PhoneRest create(@RequestBody() PhoneRest phone) {
        Phone entity = toEntity(phone);
        Phone create = phoneService.createOrUpdate(entity);
        return toRest(create);
    }

    @PostMapping("/update")
    public PhoneRest update(@RequestBody() PhoneRest phone) {
        Phone entity = toEntity(phone);
        Phone update = phoneService.createOrUpdate(entity);
        return toRest(update);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody() PhoneRest phone) {
        phoneService.delete(phone.getId());
    }

    private List<PhoneRest> collectionToRest(@NonNull Collection<Phone> vdsSet) {
        return vdsSet.stream().map(this::toRest).collect(Collectors.toList());
    }

    private PhoneRest toRest(@NonNull Phone entity) {
        PhoneRest rest = new PhoneRest();
        rest.setId(entity.getId());
        rest.setNote(entity.getNote());
        rest.setNumber(entity.getNumber());
        rest.setIsActive(entity.getStatus());
        rest.setOperatorUrl(entity.getOperatorUrl());
        rest.setOperatorType(entity.getOperatorName());
        rest.setOperatorAccLogin(entity.getOperatorAccLogin());
        rest.setOperatorAccPassword(entity.getOperatorAccPassword());
        rest.setRegDate(entity.getRegDate() != null ? entity.getRegDate().getTime() : -1);
        rest.setSocialAccountIds(
                nonNull(entity.getAccounts())
                        ? entity.getAccounts().stream().map(SocialAccount::getId).collect(Collectors.toList())
                        : Lists.newArrayList());
        return rest;
    }

    private Phone toEntity(@NonNull PhoneRest rest) {
        Phone entity = new Phone();
        entity.setId(rest.getId());
        entity.setNote(rest.getNote());
        entity.setNumber(rest.getNumber());
        entity.setStatus(rest.getIsActive());
        entity.setOperatorUrl(rest.getOperatorUrl());
        entity.setOperatorName(rest.getOperatorType());
        entity.setOperatorAccLogin(rest.getOperatorAccLogin());
        entity.setOperatorAccPassword(rest.getOperatorAccPassword());
        entity.setRegDate(rest.getRegDate() != 0 ? new Timestamp(rest.getRegDate()) : null);
        if (nonNull(rest.getSocialAccountIds()) && !rest.getSocialAccountIds().isEmpty()) {
            Set<SocialAccount> accounts = Sets.newHashSet();
            rest.getSocialAccountIds().forEach(id -> {
                SocialAccount account = new SocialAccount();
                account.setId(id);
                accounts.add(account);
            });
            entity.setAccounts(accounts);
        }
        return entity;
    }
}
