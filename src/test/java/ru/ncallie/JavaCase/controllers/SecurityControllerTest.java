package ru.ncallie.JavaCase.controllers;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;
import ru.ncallie.JavaCase.configuration.security.JWT.JWTUtil;
import ru.ncallie.JavaCase.dto.UserDto;
import ru.ncallie.JavaCase.models.User;
import ru.ncallie.JavaCase.services.UserService;
import ru.ncallie.JavaCase.utils.Convert;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
class SecurityControllerTest {

    @Autowired
    private SecurityController securityController;

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private Convert convert;

    @MockBean
    private UserService userService;


    @Test
    void registrationOk() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        UserDto userDto = new UserDto("username", "password");
        when(convert.toUser(userDto)).thenReturn(new User(1l, "username", "password"));
        when(jwtUtil.generateToken("username")).thenReturn("Bearer token");

        ResponseEntity registration = securityController.registration(userDto, bindingResult);
        Assert.assertEquals(registration.getBody().toString(), "{jwt-token=Bearer token}");

    }

    @Test
    void refreshTokenOk() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        UserDto userDto = new UserDto("username", "password");
        when(convert.toUser(userDto)).thenReturn(new User(1l, "username", "password"));
        when(jwtUtil.generateToken("username")).thenReturn("Bearer token");

        ResponseEntity response = securityController.refreshToken(userDto, bindingResult);
        Assert.assertEquals(response.getBody().toString(), "{jwt-token=Bearer token}");

    }
}