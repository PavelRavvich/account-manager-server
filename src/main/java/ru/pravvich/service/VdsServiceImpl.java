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

/**
 * @author Pavel Ravvich.
 *
 * Service layer for Vds entity.
 */
@Service
public class VdsServiceImpl implements VdsService {
    /**
     * Repository for Vds.
     */
    private final VdsRepository vdsRepository;
    /**
     * Repository for SocialAccounts.
     */
    private final SocialAccountRepository socialAccountRepository;

    /**
     * Default constructor.
     * Injection repository layer.
     *
     * @param vdsRepository repo Vds.
     * @param socialAccountRepository repo SocialAccount.
     */
    @Autowired
    public VdsServiceImpl(@NonNull VdsRepository vdsRepository, @NonNull SocialAccountRepository socialAccountRepository) {
        this.vdsRepository = vdsRepository;
        this.socialAccountRepository = socialAccountRepository;
    }

    /**
     * Get Vds list by filter.
     *
     * @param filter from UI for select by criteria.
     * @return list of Vds corresponding filter state.
     */
    @Override
    public Page<Vds> list(@NonNull final VdsFilter filter) {
        VdsSpecification specs = new VdsSpecification(filter);
        return vdsRepository.findAll(where(specs), filter.getPageable());
    }

    /**
     * Get Vds by id.
     *
     * @param id for select.
     * @return Vds with equal id or empty Vds obj.
     */
    @Override
    public Vds get(final int id) {
        Vds vds = vdsRepository.findOne(id);
        return nonNull(vds) ? vds : new Vds();
    }

    /**
     * Save new Vds or update if vds.getId() already exist.
     *
     * @param vds actual state for save to db.
     * @return created or updated Vds.
     */
    @Override
    public Vds saveOrUpdate(@NonNull final Vds vds) {
        return vdsRepository.save(vds);
    }

    /**
     * Delete Vds by id.
     *
     * @param id of Vds for delete.
     */
    @Override
    public void delete(final int id) {
        socialAccountRepository.setVds(null, id);
        vdsRepository.delete(id);
    }
}
