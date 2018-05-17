package ru.pravvich.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.pravvich.domain.SocialAccount;
import ru.pravvich.repository.SocialAccountRepository;
import ru.pravvich.repository.SocialAccountRepository.SocialAccountFilter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class SocialAccountServiceImplTest {

    @Autowired
    private SocialAccountService socialAccountService;

    @MockBean
    private SocialAccountRepository socialAccountRepository;

    private SocialAccount socialAccount;

    private SocialAccountFilter filter = new SocialAccountFilter(new PageRequest(0, 1));

    @Before
    public void before() {
        socialAccount = new SocialAccount();
        socialAccount.setId(1);
    }

    @Test
    public void whenGetByIdThenReturnSocialAccount() {
        given(socialAccountRepository.findOne(1)).willReturn(socialAccount);
        assertThat(socialAccountService.get(1), is(socialAccount));
    }

    @Test
    public void whenGetByIdWhichNotExistReturnEmptySocialAccount() {
        assertThat(socialAccountService.get(1), is(new SocialAccount()));
    }

    @Test
    public void whenSaveOrUpdateThenReturnSavedSocialAccount() {
        given(socialAccountRepository.save(socialAccount)).willReturn(socialAccount);
        assertThat(socialAccountService.saveOrUpdate(socialAccount), is(socialAccount));
    }

    @Test(expected = NullPointerException.class)
    public void whenSaveOrUpdateCallWithNullParamThenThrowNPE() {
        socialAccountService.saveOrUpdate(null);
    }

    @Test
    public void whenDeleteThenCallSocialAccountRepositoryCallDelete() {
        socialAccountService.delete(1);
        verify(socialAccountRepository, times(1)).delete(1);
    }

    @Test
    public void whenCallListThenRepoIsCalledFindAll() {
        socialAccountService.list(filter);
        verify(socialAccountRepository).findAll(isA(Specification.class), any(Pageable.class));
    }

    @Test(expected = NullPointerException.class)
    public void whenCallListWithNullThen() {
        socialAccountService.list(null);
    }
}