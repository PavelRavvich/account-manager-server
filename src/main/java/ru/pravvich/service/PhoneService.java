package ru.pravvich.service;

import lombok.Data;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.pravvich.domain.Phone;
import ru.pravvich.repository.PhoneRepository;
import ru.pravvich.repository.SocialAccountRepository;

import java.sql.Timestamp;
import java.util.Collection;

import static java.util.Objects.nonNull;
import static org.springframework.data.jpa.domain.Specifications.where;
import static ru.pravvich.repository.PhoneRepository.Specifications.*;

@Service
public class PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private SocialAccountRepository socialAccountRepository;

    public Phone get(int id) {
        Phone phone = phoneRepository.findOne(id);
        return nonNull(phone) ? phone : new Phone();
    }

    public Collection<Phone> list(@NonNull Filter filter) {
        return phoneRepository.findAll(where(equal("id", filter.getId()))
                .or(contain("operatorAccPassword", filter.getPassword()))
                .or(isBetween("regDate", filter.getRegFrom(), filter.getRegTo()))
                .or(contain("operatorName", filter.getOperatorName()))
                .or(contain("operatorAccLogin", filter.getLogin()))
                .or(contain("number", filter.getNumber()))
                .or(equal("status", filter.getStatus()))
                .or(contain("note", filter.getNote()))
        );
    }

    public Phone saveOrUpdate(@NonNull Phone phone) {
        return phoneRepository.save(phone);
    }

    public void delete(int id) {
        socialAccountRepository.setPhone(null, id);
        phoneRepository.delete(id);
    }

    @Data
    public static class Filter {

        private PageRequest pageRequest;

        private Integer id;

        private Timestamp regFrom;

        private Timestamp regTo;

        private String number;

        private String login;

        private String password;

        private String operatorName;

        private String status;

        private String note;

    }
}
