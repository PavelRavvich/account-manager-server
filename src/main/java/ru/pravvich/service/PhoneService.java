package ru.pravvich.service;

import lombok.Data;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.pravvich.domain.Phone;
import ru.pravvich.repository.PhoneRepository;
import ru.pravvich.repository.SocialAccountRepository;

import java.sql.Timestamp;

import static java.util.Objects.nonNull;
import static org.springframework.data.jpa.domain.Specifications.where;
import static ru.pravvich.repository.PhoneRepository.PhoneSpecification;

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

    public Page<Phone> list(@NonNull PhoneFilter filter) {
        PhoneSpecification specification = new PhoneSpecification(filter);
        return phoneRepository.findAll(where(specification), filter.getPageable());
    }

    public Phone saveOrUpdate(@NonNull Phone phone) {
        return phoneRepository.save(phone);
    }

    public void delete(int id) {
        socialAccountRepository.setPhone(null, id);
        phoneRepository.delete(id);
    }

    @Data
    public static class PhoneFilter {

        private Pageable pageable;

        private Integer id;

        private Timestamp regFrom;

        private Timestamp regTo;

        private String number;

        private String opLogin;

        private String opPassword;

        private String opName;

        private String status;

        private String note;

    }
}
