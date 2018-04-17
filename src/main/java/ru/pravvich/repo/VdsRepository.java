package ru.pravvich.repo;

import com.google.common.collect.Lists;
import ru.pravvich.web.vds.VdsRest;

import java.util.List;

import static java.util.Objects.nonNull;

public interface VdsRepository /* extends CrudRepository<VdsRest, Integer> */ {
    List<VdsRest> dataSource = Lists.newArrayList();

    default VdsRest getById(int id) {
        return dataSource.stream().filter(item -> item.getId() == id).findFirst().orElse(new VdsRest());
    }

    default List<VdsRest> getAll() {
        return dataSource;
    }

    default VdsRest add(VdsRest vdsRest) {
        vdsRest.setId(dataSource.size());
        dataSource.add(vdsRest);
        return vdsRest;
    }

    default VdsRest update(VdsRest vdsRest) {
        VdsRest result = dataSource.stream().filter(item -> item.getId() == vdsRest.getId()).findFirst().orElse(null);
        if (nonNull(result)) {
            result.setId(vdsRest.getId());
            //...
        }
        return result;
    }

    default boolean delete(VdsRest vdsRest) {
        dataSource.remove(vdsRest.getId());
        return true;
    }

}
