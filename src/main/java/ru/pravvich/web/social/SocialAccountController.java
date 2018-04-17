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

    @GetMapping("/get_all")
    public List<SocialAccountRest> getAll() {
        return collectionToRest(socialAccountService.getList());
    }

    @GetMapping("/get")
    public SocialAccountRest get(@RequestParam(name = "id") int id) {
        return socialAccountService.getSocialAccountById(id);
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

    private SocialAccountRest toRest(@NonNull SocialAccount account) {
        SocialAccountRest result = new SocialAccountRest();
        result.setStatus(account.getStatus());
        result.setId(account.getId());
        result.setVdsId(account.getVdsId());
        result.setNotes(account.getNotes());
        result.setLogin(account.getLogin());
        result.setPassword(account.getPassword());
        result.setPhone(account.getPhoneNumber());
        result.setSocialType(account.getSocialType());
        result.setRegDate(account.getRegDate() != null ? account.getRegDate().getTime() : -1);
        return result;
    }

    private SocialAccount toEntity(@NonNull SocialAccountRest account) {
        SocialAccount result = new SocialAccount();
        result.setStatus(account.getStatus());
        result.setNotes(account.getNotes());
        result.setLogin(account.getLogin());
        result.setVdsId(account.getVdsId());
        result.setPassword(account.getPassword());
        result.setSocialType(account.getSocialType());
        result.setRegDate(account.getRegDate() != 0 ? new Timestamp(account.getRegDate()) : null);
        if (nonNull(account.getPhone())) {
            Phone phone = new Phone();
            phone.setNumber(account.getPhone());
            result.setPhone(phone);
        }
        return result;
    }
}
