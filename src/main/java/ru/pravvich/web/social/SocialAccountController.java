package ru.pravvich.web.social;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.pravvich.config.api.RestApi;
import ru.pravvich.domain.Phone;
import ru.pravvich.domain.SocialAccount;
import ru.pravvich.service.SocialAccountService;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@RestApi
@RestController
@RequestMapping("/social_account")
public class SocialAccountController {

    @Autowired
    private SocialAccountService socialAccountService;

    @GetMapping("/get")
    public SocialAccountRest get(@RequestParam(name = "id") int id) {
        return socialAccountService.getSocialAccountById(id);
    }

    @GetMapping("/list")
    public List<SocialAccountRest> list() {
        return collectionToRest(socialAccountService.list());
    }

    @PostMapping("/create")
    public SocialAccountRest create(@RequestBody() SocialAccountRest account) {
        return toRest(socialAccountService.create(toEntity(account)));
    }

    @PostMapping("/update")
    public SocialAccountRest update(@RequestBody() SocialAccountRest account) {
        return toRest(socialAccountService.update(toEntity(account)));
    }

    @PostMapping("/delete")
    public void delete(@RequestBody int id) {
        socialAccountService.delete(id);
    }

    private List<SocialAccountRest> collectionToRest(@NonNull Collection<SocialAccount> vdsSet) {
        return vdsSet.stream().map(this::toRest).collect(Collectors.toList());
    }

    private SocialAccountRest toRest(@NonNull SocialAccount entity) {
        SocialAccountRest rest = new SocialAccountRest();
        rest.setStatus(entity.getStatus());
        rest.setId(entity.getId());
        rest.setVdsId(entity.getVdsId());
        rest.setNote(entity.getNote());
        rest.setLogin(entity.getLogin());
        rest.setPassword(entity.getPassword());
        rest.setSocialType(entity.getSocialType());
        rest.setRegDate(entity.getRegDate() != null ? entity.getRegDate().getTime() : -1);
        return rest;
    }

    private SocialAccount toEntity(@NonNull SocialAccountRest rest) {
        SocialAccount entity = new SocialAccount();
        entity.setStatus(rest.getStatus());
        entity.setNote(rest.getNote());
        entity.setLogin(rest.getLogin());
        entity.setVdsId(rest.getVdsId());
        entity.setPassword(rest.getPassword());
        entity.setSocialType(rest.getSocialType());
        entity.setRegDate(rest.getRegDate() != 0 ? new Timestamp(rest.getRegDate()) : null);
        if (nonNull(rest.getPhone())) {
            Phone phone = new Phone();
            phone.setNumber(rest.getPhone());
            entity.setPhone(phone);
        }
        return entity;
    }
}
