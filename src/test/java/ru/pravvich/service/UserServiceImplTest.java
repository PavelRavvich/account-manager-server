package ru.pravvich.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.pravvich.repository.UserRepository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Pavel Ravvich.
 */
@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void whenCallGetUserByUsernameThenRepoCallFindByUsername() {
        userService.getUserByUsername("test");
        verify(userRepository, times(1)).findByUsername("test");
    }
}