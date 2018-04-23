package ru.pravvich.web.social;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.pravvich.config.api.RestApi;
import ru.pravvich.domain.SocialAccount;
import ru.pravvich.repository.SocialAccountRepository.SocialAccountFilter;
import ru.pravvich.service.SocialAccountService;
import ru.pravvich.web.common.RestList;

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
        return toRest(socialAccountService.get(id));
    }

    @GetMapping("/list")
    public RestList list(
            @RequestParam(name = "pageSize") Integer pageSize,
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "note", required = false) String note,
            @RequestParam(name = "login", required = false) String login,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "password", required = false) String password,
            @RequestParam(name = "socialType", required = false) String socialType,
            @RequestParam(name = "phoneId", required = false) Integer phoneId,
            @RequestParam(name = "vdsId", required = false) Integer vdsId,
            @RequestParam(name = "from", required = false) Long regFrom,
            @RequestParam(name = "to", required = false) Long regTo) {

        Pageable pageable = new PageRequest(pageNumber, pageSize);
        SocialAccountFilter filter = new SocialAccountFilter(pageable);
        filter.setId(id);
        filter.setNote(note);
        filter.setVdsId(vdsId);
        filter.setLogin(login);
        filter.setStatus(status);
        filter.setPhoneId(phoneId);
        filter.setPassword(password);
        filter.setSocialType(socialType);
        filter.setTo(nonNull(regTo) ? new Timestamp(regTo) : null);
        filter.setFrom(nonNull(regFrom) ? new Timestamp(regFrom) : null);

        Page<SocialAccount> page = socialAccountService.list(filter);
        List<SocialAccountRest> accounts = toRest(page.getContent());
        return new RestList<>(pageNumber, pageSize, page.getTotalPages(), accounts);
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

    private List<SocialAccountRest> toRest(@NonNull Collection<SocialAccount> vdsSet) {
        return vdsSet.stream().map(this::toRest).collect(Collectors.toList());
    }

    private SocialAccountRest toRest(@NonNull SocialAccount entity) {
        SocialAccountRest rest = new SocialAccountRest();
        rest.setSocialType(entity.getSocialType());
        rest.setPassword(entity.getPassword());
        rest.setRegDate(entity.getRegDate());
        rest.setStatus(entity.getStatus());
        rest.setLogin(entity.getLogin());
        rest.setNote(entity.getNote());
        rest.setId(entity.getId());
        rest.setVdsId(nonNull(entity.getVdsId()) ? entity.getVdsId() : null);
        rest.setPhoneId(nonNull(entity.getPhoneId()) ? entity.getPhoneId() : null);
        rest.setPhone(nonNull(entity.getPhone()) ? entity.getPhone().getNumber() : null);
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
