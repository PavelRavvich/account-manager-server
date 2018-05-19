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
import ru.pravvich.domain.Vds;
import ru.pravvich.repository.SocialAccountRepository;
import ru.pravvich.repository.VdsRepository;
import ru.pravvich.repository.VdsRepository.VdsFilter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Pavel Ravvich.
 */
@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class VdsServiceImplTest {

    @Autowired
    private VdsService vdsService;

    @MockBean
    private VdsRepository vdsRepository;

    @MockBean
    private SocialAccountRepository socialAccountRepository;

    private Vds vds;

    private VdsFilter filter = new VdsFilter(new PageRequest(0, 1));

    @Before
    public void before() {
        vds = new Vds();
        vds.setId(1);
    }

    @Test
    public void whenGetByIdThenReturnVds() {
        given(vdsRepository.findOne(1)).willReturn(vds);
        assertThat(vdsService.get(1), is(vds));
    }

    @Test
    public void whenGetByIdWhichNotExistReturnEmptyVds() {
        assertThat(vdsService.get(1), is(new Vds()));
    }

    @Test
    public void whenSaveOrUpdateThenReturnSavedVds() {
        given(vdsRepository.save(vds)).willReturn(vds);
        assertThat(vdsService.saveOrUpdate(vds), is(vds));
    }

    @Test(expected = NullPointerException.class)
    public void whenSaveOrUpdateCallWithNullParamThenThrowNPE() {
        vdsService.saveOrUpdate(null);
    }

    @Test
    public void whenDeleteThenCallSocialAccountRepositoryCallDelete() {
        vdsService.delete(1);
        verify(vdsRepository, times(1)).delete(1);
        verify(socialAccountRepository, times(1)).setVds(null, 1);
    }

    @Test
    public void whenCallListThenRepoIsCalledFindAll() {
        vdsService.list(filter);
        verify(vdsRepository).findAll(isA(Specification.class), any(Pageable.class));
    }

    @Test(expected = NullPointerException.class)
    public void whenCallListWithNullThen() {
        vdsService.list(null);
    }
}