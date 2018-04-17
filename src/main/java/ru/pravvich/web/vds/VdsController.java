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
    public VdsRest getById(@RequestParam(name = "id") int id) {
        return toRest(vdsService.getVdsById(id));
    }

    @PostMapping("/create")
    public VdsRest create(@RequestBody() VdsRest vdsRest) {
        Vds entity = toEntity(vdsRest);
        Vds add = vdsService.add(entity);
        return toRest(add);
    }

    @PostMapping("/update")
    public VdsRest update(@RequestBody() VdsRest vdsRest) {
        Vds entity = toEntity(vdsRest);
        Vds update = vdsService.update(entity);
        return toRest(update);
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody() int id) {
        return vdsService.delete(id);
    }

    @GetMapping("/get_all")
    public List<VdsRest> getAll() {
        return collectionToRest(vdsService.getVdsList());
    }

    private List<VdsRest> collectionToRest(Collection<Vds> vdsSet) {
        return vdsSet.stream().map(this::toRest).collect(Collectors.toList());
    }

    private VdsRest toRest(@NonNull Vds vds) {
        return new VdsRest(
                vds.getId(),
                vds.getIp(),
                vds.getLogin(),
                vds.getPassword(),
                nonNull(vds.getActivatedDate()) ? vds.getActivatedDate().getTime() : null,
                nonNull(vds.getDeactivatedDate()) ? vds.getDeactivatedDate().getTime() : null
        );
    }

    private Vds toEntity(@NonNull VdsRest vds) {
        final Vds result = new Vds();
        result.setId(vds.getId());
        result.setIp(vds.getIp());
        result.setLogin(vds.getLogin());
        result.setPassword(vds.getPassword());
        result.setActivatedDate(vds.getActivatedDate() != 0 ? new Timestamp(vds.getActivatedDate()) : null);
        result.setDeactivatedDate(vds.getDeactivatedDate() != 0 ? new Timestamp(vds.getDeactivatedDate()) : null);
        return result;
    }
}
