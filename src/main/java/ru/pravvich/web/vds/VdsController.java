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

@RestApi
@RestController
@RequestMapping("/vds")
public class VdsController {

    @Autowired
    private VdsService vdsService;

    @GetMapping("/list")
    public RestList list(
            @RequestParam(name = "pageSize") Integer pageSize,
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "ip", required = false) String ip,
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "note", required = false) String note,
            @RequestParam(name = "login", required = false) String login,
            @RequestParam(name = "password", required = false) String password,
            @RequestParam(name = "password", required = false) Boolean isActivatedDate,
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

    @GetMapping("/get")
    public VdsRest get(@RequestParam(name = "id") int id) {
        return toRest(vdsService.get(id));
    }

    @PostMapping("/save")
    public VdsRest save(@RequestBody() VdsRest vds) {
        Vds entity = toEntity(vds);
        Vds saved = vdsService.saveOrUpdate(entity);
        return toRest(saved);
    }

    @PostMapping("/update")
    public VdsRest update(@RequestBody() VdsRest vds) {
        Vds entity = toEntity(vds);
        Vds update = vdsService.saveOrUpdate(entity);
        return toRest(update);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody() int id) {
        vdsService.delete(id);
    }

    private Collection<VdsRest> toRest(@NonNull Collection<Vds> vds) {
        return vds.stream().map(this::toRest).collect(Collectors.toList());
    }

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
