package ru.pravvich.service;

import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pravvich.domain.Vds;
import ru.pravvich.repository.SocialAccountRepository;
import ru.pravvich.repository.VdsRepository;

import java.util.Collection;

import static java.util.Objects.nonNull;

@Service
public class VdsService {

    @Autowired
    private VdsRepository vdsRepository;

    @Autowired
    private SocialAccountRepository socialAccountRepository;

    public Collection<Vds> list() {
        return Lists.newArrayList(vdsRepository.findAll());
    }

    public Vds get(int id) {
        Vds vds = vdsRepository.findOne(id);
        return nonNull(vds) ? vds : new Vds();
    }

    public Vds saveOrUpdate(@NonNull Vds vds) {
        return vdsRepository.save(vds);
    }

    public void delete(@NonNull Vds vds) {
        socialAccountRepository.setVdsId(null, vds.getId());
        vdsRepository.delete(vds.getId());
    }

}
