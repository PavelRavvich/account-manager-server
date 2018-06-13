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

/**
 * Controller for SocialAccount entities.
 *
 * @author Pavel Ravvich.
 */
@RestApi
@RestController
@RequestMapping("/social_account")
public class SocialAccountController {
    /**
     * Service for SocialAccount.
     */
    @NonNull
    private final SocialAccountService socialAccountService;

    /**
     * Default constructor.
     *
     * @param socialAccountService injection service layer.
     */
    @Autowired
    public SocialAccountController(@NonNull SocialAccountService socialAccountService) {
        this.socialAccountService = socialAccountService;
    }

    /**
     * Get list of SocialAccount.
     * Support pagination.
     *
     * @param pageSize   max amount element in result list.
     * @param pageNumber offset of page. Value 0 is 1 page.
     * @param id         filter value which must contains for equal.
     * @param note       filter value which must contains for match.
     * @param login      filter value which must contains for match.
     * @param status     filter value which must contains for equal.
     * @param password   filter value which must contains for match.
     * @param socialType filter value which must contains for equal.
     * @param phoneId    filter value which must contains for equal.
     * @param vdsId      filter value which must contains for equal.
     * @param from       filter start range (inclusive)
     * @param to         filter start range (inclusive)
     * @return list of SocialAccounts by filter params.
     */
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
            @RequestParam(name = "regFrom", required = false) Long from,
            @RequestParam(name = "regTo", required = false) Long to) {

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
        filter.setTo(nonNull(to) ? new Timestamp(to) : null);
        filter.setFrom(nonNull(from) ? new Timestamp(from) : null);

        Page<SocialAccount> page = socialAccountService.list(filter);
        List<SocialAccountRest> accounts = toRest(page.getContent());
        return new RestList<>(pageNumber, pageSize, page.getTotalPages(), accounts);
    }

    /**
     * Get SocialAccount By id.
     *
     * @param id for select.
     * @return SocialAccount with corresponding id.
     */
    @GetMapping("/get")
    public SocialAccountRest get(@RequestParam(name = "id") int id) {
        return toRest(socialAccountService.get(id));
    }

    /**
     * Save new SocialAccount.
     *
     * @param account for save.
     * @return saved vds with generated id.
     */
    @PostMapping("/save")
    public SocialAccountRest save(@RequestBody() SocialAccountRest account) {
        SocialAccount entity = toEntity(account);
        SocialAccount save = socialAccountService.saveOrUpdate(entity);
        return toRest(save);
    }

    /**
     * Update existed SocialAccount.
     *
     * @param account for update.
     * @return updated version.
     */
    @PostMapping("/update")
    public SocialAccountRest update(@RequestBody() SocialAccountRest account) {
        SocialAccount entity = toEntity(account);
        SocialAccount update = socialAccountService.saveOrUpdate(entity);
        return toRest(update);
    }

    /**
     * Delete SocialAccount by id.
     *
     * @param id of SocialAccount for deleting.
     */
    @PostMapping("/delete")
    public void delete(@RequestBody int id) {
        socialAccountService.delete(id);
    }

    /**
     * Convert regFrom collection SocialAccount entity regTo collection of SocialAccountRest.
     *
     * @param entity for convert.
     * @return converted for rest api.
     */
    private List<SocialAccountRest> toRest(@NonNull Collection<SocialAccount> entity) {
        return entity.stream().map(this::toRest).collect(Collectors.toList());
    }

    /**
     * Convert regFrom SocialAccount entity regTo SocialAccountRest.
     *
     * @param entity for convert.
     * @return converted for rest api.
     */
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

    /**
     * Convert regFrom SocialAccountRest regTo SocialAccount entity .
     *
     * @param rest for convert.
     * @return converted for db.
     */
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
