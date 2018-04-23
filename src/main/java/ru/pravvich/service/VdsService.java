package ru.pravvich.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.pravvich.domain.Vds;
import ru.pravvich.repository.SocialAccountRepository;
import ru.pravvich.repository.VdsRepository;
import ru.pravvich.repository.VdsRepository.VdsFilter;
import ru.pravvich.repository.VdsRepository.VdsSpecification;

import static java.util.Objects.nonNull;
import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class VdsService {

    @Autowired
    private VdsRepository vdsRepository;

    @Autowired
    private SocialAccountRepository socialAccountRepository;

    public Page<Vds> list(@NonNull final VdsFilter filter) {
        VdsSpecification specs = new VdsSpecification(filter);
        return vdsRepository.findAll(where(specs), filter.getPageable());
    }

    public Vds get(final int id) {
        Vds vds = vdsRepository.findOne(id);
        return nonNull(vds) ? vds : new Vds();
    }

    public Vds saveOrUpdate(@NonNull final Vds vds) {
        return vdsRepository.save(vds);
    }

    public void delete(final int id) {
        socialAccountRepository.setVds(null, id);
        vdsRepository.delete(id);
    }

}
