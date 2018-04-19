package ru.pravvich.web.social;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.pravvich.config.api.RestApi;
import ru.pravvich.domain.SocialAccount;
import ru.pravvich.service.SocialAccountService;

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
    public void delete(@RequestBody int id) {
        socialAccountService.delete(id);
    }

    private List<SocialAccountRest> collectionToRest(@NonNull Collection<SocialAccount> vdsSet) {
        return vdsSet.stream().map(this::toRest).collect(Collectors.toList());
    }

    private SocialAccountRest toRest(@NonNull SocialAccount entity) {
        SocialAccountRest rest = new SocialAccountRest();
        rest.setSocialType(entity.getSocialType());
        rest.setPassword(entity.getPassword());
        rest.setRegDate(entity.getRegDate());
        rest.setPhoneId(entity.getPhoneId());
        rest.setStatus(entity.getStatus());
        rest.setVdsId(entity.getVdsId());
        rest.setLogin(entity.getLogin());
        rest.setNote(entity.getNote());
        rest.setId(entity.getId());
        return rest;
    }

    private SocialAccount toEntity(@NonNull SocialAccountRest rest) {
        SocialAccount entity = new SocialAccount();
        entity.setSocialType(rest.getSocialType());
        entity.setPassword(rest.getPassword());
        entity.setPhoneId(rest.getPhoneId());
        entity.setRegDate(rest.getRegDate());
        entity.setStatus(rest.getStatus());
        entity.setLogin(rest.getLogin());
        entity.setVdsId(rest.getVdsId());
        entity.setNote(rest.getNote());
        entity.setId(rest.getId());
        return entity;
    }
}
