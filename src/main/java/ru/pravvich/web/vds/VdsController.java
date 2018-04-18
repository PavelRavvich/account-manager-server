package ru.pravvich.web.vds;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.pravvich.config.api.RestApi;
import ru.pravvich.domain.Vds;
import ru.pravvich.service.VdsService;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Objects.nonNull;

@RestApi
@RestController
@RequestMapping("/vds")
public class VdsController {

    @Autowired
    private VdsService vdsService;

    @GetMapping("/get")
    public VdsRest get(@RequestParam(name = "id") int id) {
        return toRest(vdsService.getVdsById(id));
    }

    @GetMapping("/list")
    public List<VdsRest> list() {
        return collectionToRest(vdsService.list());
    }

    @PostMapping("/create")
    public VdsRest create(@RequestBody() VdsRest vds) {
        Vds entity = toEntity(vds);
        Vds create = vdsService.create(entity);
        return toRest(create);
    }

    @PostMapping("/update")
    public VdsRest update(@RequestBody() VdsRest vdsRest) {
        Vds entity = toEntity(vdsRest);
        Vds update = vdsService.update(entity);
        return toRest(update);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody() int id) {
        vdsService.delete(id);
    }

    private List<VdsRest> collectionToRest(Collection<Vds> vdsSet) {
        return vdsSet.stream().map(this::toRest).collect(Collectors.toList());
    }

    private VdsRest toRest(@NonNull Vds entity) {
        VdsRest rest = new VdsRest();
        rest.setId(entity.getId());
        rest.setIp(entity.getIp());
        rest.setNote(entity.getNote());
        rest.setLogin(entity.getLogin());
        rest.setPassword(entity.getPassword());
        rest.setActivatedDate(nonNull(entity.getActivatedDate()) ? entity.getActivatedDate().getTime() : null);
        rest.setDeactivatedDate(nonNull(entity.getDeactivatedDate()) ? entity.getDeactivatedDate().getTime() : null);
        return rest;
    }

    private Vds toEntity(@NonNull VdsRest rest) {
        Vds entity = new Vds();
        entity.setId(rest.getId());
        entity.setIp(rest.getIp());
        entity.setLogin(rest.getLogin());
        entity.setPassword(rest.getPassword());
        entity.setActivatedDate(rest.getActivatedDate() != 0 ? new Timestamp(rest.getActivatedDate()) : null);
        entity.setDeactivatedDate(rest.getDeactivatedDate() != 0 ? new Timestamp(rest.getDeactivatedDate()) : null);
        return entity;
    }
}
