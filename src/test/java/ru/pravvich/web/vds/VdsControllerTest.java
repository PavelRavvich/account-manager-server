package ru.pravvich.web.vds;

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
import ru.pravvich.domain.Vds;
import ru.pravvich.repository.VdsRepository.VdsFilter;
import ru.pravvich.service.VdsService;
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
public class VdsControllerTest {

    @Autowired
    private VdsController vdsController;

    @MockBean
    private VdsService vdsService;

    private Vds vds;

    private VdsFilter filter;

    @Before
    public void before() {
        vds = new Vds();
        vds.setId(1);
        filter = new VdsFilter(new PageRequest(0, 1));
    }

    @Test
    public void whenCallGetThenThenGotVds() {
        given(vdsService.get(1)).willReturn(vds);
        VdsRest result = vdsController.get(1);
        assertThat(result.getId(), is(1));
    }

    @Test
    public void whenCallListWithRegDateNullThenReturnData() {
        given(vdsService.list(filter)).willReturn(new PageImpl<>(Lists.newArrayList(vds)));
        RestList result = vdsController.list(1, 0, null, null, null, null, null, null, null, null);
        assertThat(result.getData().size(), is(1));
    }

    @Test
    public void whenCallListWithRegDateNonNullThenReturnData() {
        long date = System.currentTimeMillis();
        filter.setFrom(new Timestamp(date));
        filter.setTo(new Timestamp(date));
        given(vdsService.list(filter)).willReturn(new PageImpl<>(Lists.newArrayList(vds)));
        RestList result = vdsController.list(1, 0, null, null, null, null, null, null,  date, date);
        assertThat(result.getData().size(), is(1));
    }

    @Test
    public void whenSavePhoneThenReturnSamePhone() {
        Vds vds = new Vds();
        vds.setId(1);
        given(vdsService.saveOrUpdate(vds)).willReturn(vds);
        VdsRest phoneRest = new VdsRest();
        phoneRest.setId(1);
        VdsRest result = vdsController.save(phoneRest);
        assertThat(result.getId(), is(1));
    }

    @Test
    public void whenUpdatePhoneThenReturnSamePhone() {
        Vds vds = new Vds();
        vds.setId(1);
        given(vdsService.saveOrUpdate(vds)).willReturn(vds);
        VdsRest vdsRest = new VdsRest();
        vdsRest.setId(1);
        VdsRest result = vdsController.update(vdsRest);
        assertThat(result.getId(), is(1));
    }

    @Test
    public void whenDelete() {
        vdsController.delete(1);
        verify(vdsService).delete(1);
    }

    @Test
    public void whenEquals() {
        VdsRest first = new VdsRest();
        first.setId(1);
        VdsRest second = new VdsRest();
        second.setId(1);
        assertEquals(first, second);
    }
}