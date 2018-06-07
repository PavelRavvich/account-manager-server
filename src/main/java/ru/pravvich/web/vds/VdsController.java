package ru.pravvich.web.vds;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.pravvich.config.api.RestApi;
import ru.pravvich.domain.Vds;
import ru.pravvich.repository.VdsRepository.VdsFilter;
import ru.pravvich.service.VdsService;
import ru.pravvich.web.common.RestList;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * Controller for Vds entities.
 *
 * @author Pavel Ravvich.
 */
@RestApi
@RestController
@RequestMapping("/vds")
public class VdsController {
    /**
     * Service for Vds.
     */
    @NonNull
    private final VdsService vdsService;

    /**
     * Default constructor.
     *
     * @param vdsService injection service layer.
     */
    @Autowired
    public VdsController(@NonNull VdsService vdsService) {
        this.vdsService = vdsService;
    }

    /**
     * Get list of vds.
     * Support pagination.
     *
     * @param pageSize        max amount element in result list.
     * @param pageNumber      offset of page. Value 0 is 1 page.
     * @param ip              filter value which must contains for match.
     * @param id              filter value which must contains for match.
     * @param note            filter value which must contains for match.
     * @param login           filter value which must contains for match.
     * @param password        filter value which must contains for match.
     * @param isActivatedDate filter parameter specify date filter. Con be by activated date or deactivated date.
     * @param from            filter start range (inclusive)
     * @param to              filter end range (inclusive)
     * @return list of vds by filter params.
     */
    @GetMapping("/list")
    public RestList<VdsRest> list(
            @RequestParam(name = "pageSize") Integer pageSize,
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "ip", required = false) String ip,
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "note", required = false) String note,
            @RequestParam(name = "login", required = false) String login,
            @RequestParam(name = "password", required = false) String password,
            @RequestParam(name = "isActivatedDate", required = false) Boolean isActivatedDate,
            @RequestParam(name = "from", required = false) Long from,
            @RequestParam(name = "to", required = false) Long to) {

        Pageable pageable = new PageRequest(pageNumber, pageSize);
        VdsFilter filter = new VdsFilter(pageable);
        filter.setId(id);
        filter.setIp(ip);
        filter.setNote(note);
        filter.setLogin(login);
        filter.setPassword(password);
        filter.setIsActivatedDate(isActivatedDate);
        filter.setTo(nonNull(to) ? new Timestamp(to) : null);
        filter.setFrom(nonNull(from) ? new Timestamp(from) : null);

        Page<Vds> page = vdsService.list(filter);
        Collection<VdsRest> vds = toRest(page.getContent());
        return new RestList<>(pageNumber, pageSize, page.getTotalPages(), vds);
    }

    /**
     * Get vds By id.
     *
     * @param id for select.
     * @return vds with corresponding id.
     */
    @GetMapping("/get")
    public VdsRest get(@RequestParam(name = "id") int id) {
        return toRest(vdsService.get(id));
    }

    /**
     * Save new vds.
     *
     * @param vds for save.
     * @return saved vds with generated id.
     */
    @PostMapping("/save")
    public VdsRest save(@RequestBody() VdsRest vds) {
        Vds entity = toEntity(vds);
        Vds saved = vdsService.saveOrUpdate(entity);
        return toRest(saved);
    }

    /**
     * Update existed vds.
     *
     * @param vds for update.
     * @return updated version.
     */
    @PostMapping("/update")
    public VdsRest update(@RequestBody() VdsRest vds) {
        Vds entity = toEntity(vds);
        Vds update = vdsService.saveOrUpdate(entity);
        return toRest(update);
    }

    /**
     * Delete vds bu id.
     *
     * @param id of vds for deleting.
     */
    @PostMapping("/delete")
    public void delete(@RequestBody() int id) {
        vdsService.delete(id);
    }

    /**
     * Convert from collection Vds entity to collection of VdsRest.
     *
     * @param entity for convert.
     * @return converted for rest api.
     */
    private Collection<VdsRest> toRest(@NonNull Collection<Vds> entity) {
        return entity.stream().map(this::toRest).collect(Collectors.toList());
    }

    /**
     * Convert from Vds entity to VdsRest.
     *
     * @param entity for convert.
     * @return converted for rest api.
     */
    private VdsRest toRest(@NonNull Vds entity) {
        VdsRest rest = new VdsRest();
        rest.setId(entity.getId());
        rest.setIp(entity.getIp());
        rest.setNote(entity.getNote());
        rest.setLogin(entity.getLogin());
        rest.setPassword(entity.getPassword());
        rest.setActivated(entity.getActivated());
        rest.setDeactivated(entity.getDeactivated());
        return rest;
    }

    /**
     * Convert from VdsRest to Vds entity .
     *
     * @param rest for convert.
     * @return converted for db.
     */
    private Vds toEntity(@NonNull VdsRest rest) {
        Vds entity = new Vds();
        entity.setId(rest.getId());
        entity.setIp(rest.getIp());
        entity.setNote(rest.getNote());
        entity.setLogin(rest.getLogin());
        entity.setPassword(rest.getPassword());
        entity.setActivated(rest.getActivated());
        entity.setDeactivated(rest.getDeactivated());
        return entity;
    }
}
