package ru.pravvich.web.phone;

import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.pravvich.config.api.RestApi;
import ru.pravvich.domain.Phone;
import ru.pravvich.domain.SocialAccount;
import ru.pravvich.service.PhoneService;
import ru.pravvich.web.common.RestList;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static ru.pravvich.repository.PhoneRepository.PhoneFilter;

/**
 * @author Pavel Ravvich.
 */
@RestApi
@RestController
@RequestMapping("/phone")
public class PhoneController {

    @NonNull
    private final PhoneService phoneService;

    @Autowired
    public PhoneController(@NonNull PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @GetMapping("/list")
    public RestList list(
            @RequestParam(name = "pageSize") Integer pageSize,
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "note", required = false) String note,
            @RequestParam(name = "num", required = false) String number,
            @RequestParam(name = "login", required = false) String opLogin,
            @RequestParam(name = "password", required = false) String opPassword,
            @RequestParam(name = "opName", required = false) String opName,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "from", required = false) Long from,
            @RequestParam(name = "to", required = false) Long to) {

        Pageable pageable = new PageRequest(pageNumber, pageSize);
        PhoneFilter filter = new PhoneFilter(pageable);
        filter.setId(id);
        filter.setNote(note);
        filter.setStatus(status);
        filter.setNumber(number);
        filter.setOpName(opName);
        filter.setLogin(opLogin);
        filter.setPassword(opPassword);
        filter.setTo(nonNull(to) ? new Timestamp(to) : null);
        filter.setFrom(nonNull(from) ? new Timestamp(from) : null);

        Page<Phone> page = phoneService.list(filter);
        Collection<PhoneRest> phones = toRest(page.getContent());
        return new RestList<>(pageNumber, pageSize, page.getTotalPages(), phones);
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
        rest.setRegDate(entity.getRegDate());
        rest.setOpUrl(entity.getOperatorUrl());
        rest.setOpType(entity.getOperatorName());
        rest.setOpLogin(entity.getOperatorAccLogin());
        rest.setOpPassword(entity.getOperatorAccPassword());
        rest.setSocialAccIds(nonNull(entity.getAccounts())
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
        entity.setOperatorUrl(rest.getOpUrl());
        entity.setOperatorName(rest.getOpType());
        entity.setOperatorAccLogin(rest.getOpLogin());
        entity.setOperatorAccPassword(rest.getOpPassword());
        entity.setRegDate(rest.getRegDate());
        entity.setAccounts(Lists.newArrayList());
        if (nonNull(rest.getSocialAccIds())) {
            rest.getSocialAccIds().forEach(id -> {
                SocialAccount account = new SocialAccount();
                account.setId(id);
                entity.getAccounts().add(account);
            });
        }
        return entity;
    }
}
