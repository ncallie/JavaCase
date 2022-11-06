package ru.ncallie.JavaCase.configuration.security.JWT;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ncallie.JavaCase.models.User;
import ru.ncallie.JavaCase.services.UserService;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
class JWTFilterTest {

    @Autowired
    private JWTFilter jwtFilter;

    @MockBean
    HttpServletRequest request;
    @MockBean
    FilterChain filterChain;

    @MockBean
    UserService userService;

    @MockBean
    SecurityContextHolder securityContextHolder;


    @SneakyThrows
    @Test
    void doFilterInternalOk(){
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        when(request.getHeader(AUTHORIZATION)).thenReturn("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJpc3MiOiJuY2FsbGllIiwiZXhwIjoxNjY3ODE5Nzc0LCJpYXQiOjE2Njc3MzMzNzQsInVzZXJuYW1lIjoicm9tYW5iaW5lbCJ9.SDQdNB9z6kzn2Hkdyq8bXXL26j8ueSls_XyZuZZDmNw");
        when(userService.loadUserByUsername("romanbinel")).thenReturn(new User(1l, "romanbinel", "password"));
        jwtFilter.doFilterInternal(request, httpServletResponse, filterChain);
    }
}