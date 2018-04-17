package ru.pravvich.service;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pravvich.domain.Vds;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;
import java.util.stream.IntStream;

@Service
public class VdsService {

    @Autowired
    private SocialAccountService socialAccountService;

    public Vds getVdsById(int id) {
        System.out.println("GET VDS BY ID: " + id);
        return createMockVDS();
    }

    public Collection<Vds> getVdsList() {
        Set<Vds> result = Sets.newHashSet();
        IntStream.range(0, 30).forEach(i -> {
            Vds mockVDS = createMockVDS();
            mockVDS.setId(i + 1);
            result.add(mockVDS);
        });
        return result;
    }

    public Vds add(Vds vds) {
        return vds;
    }

    public Vds update(Vds vds) {
        return vds;
    }

    public boolean delete(int id) {
        return true;
    }

    public Vds createMockVDS() {
        Vds vds = new Vds();
        vds.setIp("123.456.67.89");
        vds.setLogin("test_login");
        vds.setPassword("test_password");
        vds.setActivatedDate(new Timestamp(System.currentTimeMillis() - 86400000));
        vds.setDeactivatedDate(new Timestamp(System.currentTimeMillis()));
        vds.setAccounts(Sets.newHashSet(socialAccountService.getMockSocialAccount()));
        return vds;
    }
}
