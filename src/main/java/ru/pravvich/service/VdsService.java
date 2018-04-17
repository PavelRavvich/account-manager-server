package ru.pravvich.service;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import ru.pravvich.domain.Vds;

import java.sql.Timestamp;
import java.util.*;

@Service
public class VdsService {

    public Vds getVdsById(int id) {
        return new Vds();
    }

    public Collection<Vds> getVdsList() {
        return Lists.newArrayList(createMockVDS());
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

    private Vds createMockVDS() {
        Vds vds = new Vds();
        vds.setId(1);
        vds.setIp("123.456.67.89");
        vds.setLogin("test_login");
        vds.setPassword("test_password");
        vds.setActivatedDate(new Timestamp(System.currentTimeMillis() - 86400000));
        vds.setDeactivatedDate(new Timestamp(System.currentTimeMillis()));
        return vds;
    }
}
