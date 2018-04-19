package ru.pravvich.web.phone;

import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.pravvich.config.api.RestApi;
import ru.pravvich.domain.Phone;
import ru.pravvich.domain.SocialAccount;
import ru.pravvich.service.PhoneService;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@RestApi
@RestController
@RequestMapping("/phone")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @GetMapping("/list")
    public Collection<PhoneRest> list(
            @RequestParam(name = "pageSize") Integer pageSize,
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "note", required = false) String note,
            @RequestParam(name = "num", required = false) String number,
            @RequestParam(name = "login", required = false) String login,
            @RequestParam(name = "password", required = false) String password,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "operatorName", required = false) String operatorName,
            @RequestParam(name = "regFrom", required = false) Long regFrom,
            @RequestParam(name = "regTo", required = false) Long regTo) {

        PhoneService.Filter filter = new PhoneService.Filter();
        filter.setStatus(status);
        filter.setNumber(number);
        filter.setNote(note);
        filter.setId(id);
        filter.setLogin(login);
        filter.setPassword(password);
        filter.setOperatorName(operatorName);
        filter.setRegTo(new Timestamp(regTo));
        filter.setRegFrom(new Timestamp(regFrom));
        filter.setPageRequest(new PageRequest(pageNumber, pageSize));
        return collectionToRest(phoneService.list(filter));
    }

    @GetMapping("/get")
    public PhoneRest get(@RequestParam(name = "id") int id) {
        return toRest(phoneService.get(id));
    }

    @PostMapping("/save")
    public PhoneRest save(@RequestBody() PhoneRest phone) {
        Phone entity = toEntity(phone);
        Phone saved = phoneService.saveOrUpdate(entity);
        return toRest(saved);
    }

    @PostMapping("/update")
    public PhoneRest update(@RequestBody() PhoneRest phone) {
        Phone entity = toEntity(phone);
        Phone update = phoneService.saveOrUpdate(entity);
        return toRest(update);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody() int id) {
        phoneService.delete(id);
    }

    private Collection<PhoneRest> collectionToRest(@NonNull Collection<Phone> phones) {
        return phones.stream().map(this::toRest).collect(Collectors.toList());
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
        rest.setRegDate(entity.getRegDate());
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
        entity.setRegDate(rest.getRegDate());
        entity.setAccounts(Lists.newArrayList());
        if (nonNull(rest.getSocialAccountIds())) {
            rest.getSocialAccountIds().forEach(id -> {
                SocialAccount account = new SocialAccount();
                account.setId(id);
                entity.getAccounts().add(account);
            });
        }
        return entity;
    }
}
