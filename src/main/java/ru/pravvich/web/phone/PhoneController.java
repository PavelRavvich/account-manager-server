package ru.pravvich.web.phone;

import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public PhoneListRest list(
            @RequestParam(name = "pageSize") Integer pageSize,
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "note", required = false) String note,
            @RequestParam(name = "num", required = false) String number,
            @RequestParam(name = "opLogin", required = false) String opLogin,
            @RequestParam(name = "opPassword", required = false) String opPassword,
            @RequestParam(name = "opName", required = false) String opName,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "regFrom", required = false) Long regFrom,
            @RequestParam(name = "regTo", required = false) Long regTo) {

        PageRequest pageable = new PageRequest(pageNumber, pageSize);
        PhoneService.PhoneFilter filter = new PhoneService.PhoneFilter();
        filter.setId(id);
        filter.setNote(note);
        filter.setStatus(status);
        filter.setNumber(number);
        filter.setOpName(opName);
        filter.setOpLogin(opLogin);
        filter.setPageable(pageable);
        filter.setOpPassword(opPassword);
        filter.setRegTo(nonNull(regTo) ? new Timestamp(regTo) : null);
        filter.setRegFrom(nonNull(regFrom) ? new Timestamp(regFrom) : null);

        Page<Phone> page = phoneService.list(filter);
        Collection<PhoneRest> phones = toRest(page.getContent());
        return new PhoneListRest(pageNumber, pageSize, page.getTotalPages(), phones);
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

    private Collection<PhoneRest> toRest(@NonNull Collection<Phone> phones) {
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
