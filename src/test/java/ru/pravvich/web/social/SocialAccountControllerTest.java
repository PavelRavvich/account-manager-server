package ru.pravvich.web.social;

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
import ru.pravvich.domain.SocialAccount;
import ru.pravvich.repository.SocialAccountRepository.SocialAccountFilter;
import ru.pravvich.service.SocialAccountService;
import ru.pravvich.web.common.RestList;

import java.sql.Timestamp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author Pavel Ravvich.
 */
@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class SocialAccountControllerTest {

    @Autowired
    private SocialAccountController socialAccountController;

    @MockBean
    private SocialAccountService socialAccountService;

    private SocialAccountFilter filter;

    private SocialAccount account;

    @Before
    public void before() {
        account = new SocialAccount();
        account.setId(1);
        filter = new SocialAccountFilter(new PageRequest(0, 1));
    }

    @Test
    public void whenCallGetThenGotPhone() {
        given(socialAccountService.get(1)).willReturn(account);
        SocialAccountRest result = socialAccountController.get(1);
        assertThat(result.getId(), is(1));
    }

    @Test
    public void whenCallListWithRegDateNullThenReturnData() {
        given(socialAccountService.list(filter)).willReturn(new PageImpl<>(Lists.newArrayList(account)));
        RestList result = socialAccountController.list(1, 0, null, null, null, null, null, null, null, null, null, null);
        assertThat(result.getData().size(), is(1));
    }

    @Test
    public void whenCallListWithRegDateNonNullThenReturnData() {
        long date = System.currentTimeMillis();
        filter.setFrom(new Timestamp(date));
        filter.setTo(new Timestamp(date));
        given(socialAccountService.list(filter)).willReturn(new PageImpl<>(Lists.newArrayList(account)));
        RestList result = socialAccountController.list(1, 0, null, null, null, null, null, null, null, null, date, date);
        assertThat(result.getData().size(), is(1));
    }

    @Test
    public void whenSaveSocialAccountThenReturnSameSocialAccount() {
        SocialAccount account = new SocialAccount();
        account.setId(1);
        given(socialAccountService.saveOrUpdate(account)).willReturn(account);
        SocialAccountRest socialAccountRest = new SocialAccountRest();
        socialAccountRest.setId(1);
        SocialAccountRest result = socialAccountController.save(socialAccountRest);
        assertThat(result.getId(), is(1));
    }

    @Test
    public void whenUpdateSocialAccountThenReturnSameSocialAccount() {
        SocialAccount account = new SocialAccount();
        account.setId(1);
        given(socialAccountService.saveOrUpdate(account)).willReturn(account);
        SocialAccountRest socialAccountRest = new SocialAccountRest();
        socialAccountRest.setId(1);
        SocialAccountRest result = socialAccountController.update(socialAccountRest);
        assertThat(result.getId(), is(1));
    }

    @Test
    public void whenDelete() {
        socialAccountController.delete(1);
        verify(socialAccountService).delete(1);
    }

    @Test
    public void whenEquals() {
        SocialAccountRest first = new SocialAccountRest();
        first.setId(1);
        SocialAccountRest second = new SocialAccountRest();
        second.setId(1);
        assertEquals(first, second);
    }
}