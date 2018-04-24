package ru.pravvich.web.phone;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.pravvich.domain.Phone;
import ru.pravvich.service.PhoneService;

import java.sql.Timestamp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class PhoneControllerTest {

    @Autowired
    private PhoneController phoneController;

    @MockBean
    private PhoneService phoneService;

    private Phone phone;

    @Before
    public void before() {
        phone = new Phone();
        phone.setAccounts(Lists.newArrayList());
        phone.setRegDate(new Timestamp(System.currentTimeMillis()));
        phone.setOperatorAccPassword("test_password");
        phone.setOperatorUrl("test_operator_url");
        phone.setOperatorAccLogin("test_login");
        phone.setOperatorName("test_op_name");
        phone.setNumber("test_phone_number");
        phone.setStatus("test_status");
        phone.setNote("teat_note");
        phone.setId(1);
    }


    @Test
    public void whenCallGetThenGotPhone() {
        given(phoneService.get(1)).willReturn(phone);
        PhoneRest result = phoneController.get(1);
        assertThat(result.getId(), is(1));
    }
}