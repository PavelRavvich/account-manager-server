package ru.pravvich.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.pravvich.domain.Phone;
import ru.pravvich.repository.PhoneRepository;
import ru.pravvich.repository.PhoneRepository.PhoneFilter;
import ru.pravvich.repository.SocialAccountRepository;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.data.jpa.domain.Specifications.where;
import static ru.pravvich.repository.PhoneRepository.*;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class PhoneServiceImplTest {

    @Autowired
    PhoneService phoneService;

    @MockBean
    private PhoneRepository phoneRepository;

    @MockBean
    private SocialAccountRepository socialAccountRepository;

    private Phone phone;

    private PhoneFilter filter = new PhoneFilter(new PageRequest(0 , 1));

    @Before
    public void before() {
        phone = new Phone();
        phone.setId(1);
    }

    @Test
    public void whenGetByIdReturnPhone() {
        given(phoneRepository.findOne(1)).willReturn(phone);
        assertThat(phoneService.get(1), is(phone));
    }

    @Test
    public void whenGetByIdWhichNotExistReturnEmptyPhone() {
        assertThat(phoneService.get(1), is(new Phone()));
    }

    @Test
    public void whenSaveOrUpdateThenReturnSavedPhone() {
        given(phoneRepository.save(phone)).willReturn(phone);
        assertThat(phoneService.saveOrUpdate(phone), is(phone));
    }

    @Test
    public void whenDeleteThenCallSocialAccountRepositorySetPhoneAndPhoneRepositoryDelete() {
        phoneService.delete(1);
        verify(socialAccountRepository, times(1)).setPhone(null, 1);
        verify(phoneRepository, times(1)).delete(1);
    }
}