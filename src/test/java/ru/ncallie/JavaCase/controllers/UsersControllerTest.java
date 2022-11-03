package ru.ncallie.JavaCase.controllers;


import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import ru.ncallie.JavaCase.dto.UserAndGroupIdRequestDto;
import ru.ncallie.JavaCase.dto.UserDto;
import ru.ncallie.JavaCase.models.User;
import ru.ncallie.JavaCase.services.VkService;
import ru.ncallie.JavaCase.utils.Convert;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {

    @Autowired
    private UsersController usersController;
    @MockBean
    private VkService vkService;
    @MockBean
    private Convert convert;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getUserTestHttpStatusOK() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        ResponseEntity response = usersController.getUser("token", new UserAndGroupIdRequestDto(1111, 1111), bindingResult);
        Assert.assertTrue(CoreMatchers.is(response.getStatusCode()).matches(HttpStatus.OK));
    }

    @Test
    public void getUserTestHttpStatusBAD_REQUEST() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        ResponseEntity response = usersController.getUser("token", new UserAndGroupIdRequestDto(1111, 1111), bindingResult);
        Assert.assertTrue(CoreMatchers.is(response.getStatusCode()).matches(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void getUserTestBody() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        UserAndGroupIdRequestDto userAndGroupIdRequestDto = new UserAndGroupIdRequestDto(78385, 93559769);
        String token = "token";
        User user = User.builder().first_name("Иванов").last_name("Иван").nickname("Иванович").isMember(true).id(78385).build();
        UserDto userDto = UserDto.builder().first_name("Иванов").last_name("Иван").nickname("Иванович").isMember(true).build();

        when(vkService.getUser(userAndGroupIdRequestDto, token)).thenReturn(user);
        when(convert.toUserDto(user)).thenReturn(userDto);
        ResponseEntity response = usersController.getUser(token, userAndGroupIdRequestDto, bindingResult);
        Assert.assertEquals(response.getBody(), userDto);
        Assert.assertTrue(CoreMatchers.is(response.getStatusCode()).matches(HttpStatus.OK));
    }

    @Test
    public void emptyTokenAndBody() throws Exception {
        this.mockMvc.perform(post("/api/users"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void emptyBody() throws Exception {
        this.mockMvc.perform(post("/api/users/")
                .header("vk_service_token", "token"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getOk() throws Exception {
        String jsonString = "{\"user_id\": \"4\", \"group_id\": \"2\"}";
        this.mockMvc.perform(post("/api/users/")
                        .content(jsonString).contentType(MediaType.APPLICATION_JSON)
                .header("vk_service_token", "token"))
                .andExpect(status().isOk());
    }

}
