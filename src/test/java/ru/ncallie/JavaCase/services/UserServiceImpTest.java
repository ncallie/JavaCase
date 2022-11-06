package ru.ncallie.JavaCase.services;

import org.junit.Assert;
import org.junit.function.ThrowingRunnable;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ncallie.JavaCase.models.User;
import ru.ncallie.JavaCase.repositories.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
class UserServiceImpTest {

    @Autowired
    private UserServiceImp userServiceImp;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void loadUserByUsernameOk() {
        User user = new User(1l, "username", "password");
        when(userRepository.findByUsername("username"))
                .thenReturn(Optional.of(user));
        UserDetails userDetails = userServiceImp.loadUserByUsername("username");
        Assert.assertEquals(userDetails, user);
    }

    @Test
    void loadUserByUsernameOException() {
        when(userRepository.findByUsername("username"))
                .thenReturn(Optional.empty());
        Assert.assertThrows(UsernameNotFoundException.class, () -> userServiceImp.loadUserByUsername("username"));
    }
}