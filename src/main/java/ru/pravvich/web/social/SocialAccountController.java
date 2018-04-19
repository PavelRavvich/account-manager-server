package ru.pravvich.web.social;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.pravvich.config.api.RestApi;
import ru.pravvich.domain.SocialAccount;
import ru.pravvich.service.SocialAccountService;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestApi
@RestController
@RequestMapping("/social_account")
public class SocialAccountController {

    @Autowired
    private SocialAccountService socialAccountService;

    @GetMapping("/get")
    public SocialAccountRest get(@RequestParam(name = "id") int id) {
        return toRest(socialAccountService.get(id));
    }

    @GetMapping("/list")
    public List<SocialAccountRest> list() {
        return collectionToRest(socialAccountService.list());
    }

    @PostMapping("/save")
    public SocialAccountRest save(@RequestBody() SocialAccountRest account) {
        SocialAccount entity = toEntity(account);
        SocialAccount save = socialAccountService.saveOrUpdate(entity);
        return toRest(save);
    }

    @PostMapping("/update")
    public SocialAccountRest update(@RequestBody() SocialAccountRest account) {
        SocialAccount entity = toEntity(account);
        SocialAccount update = socialAccountService.saveOrUpdate(entity);
        return toRest(update);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody SocialAccountRest account) {
        socialAccountService.delete(account.getId());
    }

    private List<SocialAccountRest> collectionToRest(@NonNull Collection<SocialAccount> vdsSet) {
        return vdsSet.stream().map(this::toRest).collect(Collectors.toList());
    }

    private SocialAccountRest toRest(@NonNull SocialAccount entity) {
        SocialAccountRest rest = new SocialAccountRest();
        rest.setPhoneId(entity.getPhoneId());
        rest.setStatus(entity.getStatus());
        rest.setId(entity.getId());
        rest.setNote(entity.getNote());
        rest.setVdsId(entity.getVdsId());
        rest.setLogin(entity.getLogin());
        rest.setPassword(entity.getPassword());
        rest.setSocialType(entity.getSocialType());
        rest.setRegDate(entity.getRegDate() != null ? entity.getRegDate().getTime() : 0);
        return rest;
    }

    private SocialAccount toEntity(@NonNull SocialAccountRest rest) {
        SocialAccount entity = new SocialAccount();
        entity.setStatus(rest.getStatus());
        entity.setId(rest.getId());
        entity.setNote(rest.getNote());
        entity.setLogin(rest.getLogin());
        entity.setVdsId(rest.getVdsId());
        entity.setPhoneId(rest.getPhoneId());
        entity.setPassword(rest.getPassword());
        entity.setSocialType(rest.getSocialType());
        entity.setRegDate(rest.getRegDate() != 0 ? new Timestamp(rest.getRegDate()) : null);
        return entity;
    }
}
