package ru.pravvich.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pravvich.domain.Vds;
import ru.pravvich.repository.VdsRepository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.Objects.nonNull;

@Service
public class VdsService {

    @Autowired
    private VdsRepository vdsRepository;

    public Collection<Vds> list() {
        return Lists.newArrayList(vdsRepository.findAll());
    }

    public Vds get(int id) {
        Vds vds = vdsRepository.findOne(id);
        return nonNull(vds) ? vds : new Vds();
    }

    public Vds saveOrUpdate(Vds vds) {
        return vdsRepository.save(vds);
    }

    public void delete(int id) {
        vdsRepository.delete(id);
    }

}
