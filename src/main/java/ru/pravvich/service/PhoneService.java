package ru.pravvich.service;

import org.springframework.stereotype.Service;
import ru.pravvich.web.phone.PhoneRest;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneService {

    public PhoneRest getPhoneById() {
        return new PhoneRest();
    }

    private List<PhoneRest> getPhoneList() {
        return new ArrayList<>();
    }
}
