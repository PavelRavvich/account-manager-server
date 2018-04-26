package ru.pravvich.web.phone;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.pravvich.domain.Phone;
import ru.pravvich.repository.PhoneRepository.PhoneFilter;
import ru.pravvich.service.PhoneService;
import ru.pravvich.web.common.RestList;

import java.sql.Timestamp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class PhoneControllerTest {

    @Autowired
    private PhoneController phoneController;

    @MockBean
    private PhoneService phoneService;

    private Phone phone;

    private PhoneFilter filter;

    @Before
    public void before() {
        phone = new Phone();
        phone.setAccounts(Lists.newArrayList());
        phone.setRegDate(new Timestamp(System.currentTimeMillis()));
        phone.setId(1);
        filter = new PhoneFilter(new PageRequest(0, 1));
    }

    @Test
    public void whenCallGetThenThenGotPhone() {
        given(phoneService.get(1)).willReturn(phone);
        PhoneRest result = phoneController.get(1);
        assertThat(result.getId(), is(1));
    }

    @Test
    public void whenCallListWithRegDateNullThenReturnData() {
        given(phoneService.list(filter)).willReturn(new PageImpl<>(Lists.newArrayList(phone)));
        RestList result = phoneController.list(1, 0, null, null, null, null, null, null, null, null, null);
        assertThat(result.getData().size(), is(1));
    }

    @Test
    public void whenCallListWithRegDateNonNullThenReturnData() {
        long date = System.currentTimeMillis();
        filter.setFrom(new Timestamp(date));
        filter.setTo(new Timestamp(date));
        given(phoneService.list(filter)).willReturn(new PageImpl<>(Lists.newArrayList(phone)));
        RestList result = phoneController.list(1, 0, null, null, null, null, null, null, null, date, date);
        assertThat(result.getData().size(), is(1));
    }

    @Test
    public void whenSavePhoneThenReturnSamePhone() {
        Phone phone = new Phone();
        phone.setId(1);
        given(phoneService.saveOrUpdate(phone)).willReturn(phone);
        PhoneRest phoneRest = new PhoneRest();
        phoneRest.setId(1);
        PhoneRest result = phoneController.save(phoneRest);
        assertThat(result.getId(), is(1));
    }

    @Test
    public void whenUpdatePhoneThenReturnSamePhone() {
        Phone phone = new Phone();
        phone.setId(1);
        given(phoneService.saveOrUpdate(phone)).willReturn(phone);
        PhoneRest phoneRest = new PhoneRest();
        phoneRest.setSocialAccIds(Lists.newArrayList(1));
        phoneRest.setId(1);
        PhoneRest result = phoneController.update(phoneRest);
        assertThat(result.getId(), is(1));
    }

    @Test
    public void whenDelete() {
        phoneController.delete(1);
        verify(phoneService).delete(1);
    }
}