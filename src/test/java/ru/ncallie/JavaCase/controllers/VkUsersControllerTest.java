package ru.ncallie.JavaCase.controllers;


import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;
import ru.ncallie.JavaCase.dto.VkUserAndGroupIdRequestDto;
import ru.ncallie.JavaCase.dto.VkUserDto;
import ru.ncallie.JavaCase.models.VkUser;
import ru.ncallie.JavaCase.repositories.VKRepositoryImp;
import ru.ncallie.JavaCase.services.VkService;
import ru.ncallie.JavaCase.utils.Convert;

import java.lang.reflect.Field;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"vk_api_version=5.131"})
@AutoConfigureMockMvc
public class VkUsersControllerTest {

    @Autowired
    private VkUsersController vkUsersController;

    @Autowired
    private VKRepositoryImp vkRepositoryImp;
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
        ResponseEntity response = vkUsersController.getUser("token", new VkUserAndGroupIdRequestDto(1111, 1111), bindingResult);
        Assert.assertTrue(CoreMatchers.is(response.getStatusCode()).matches(HttpStatus.OK));
    }

    @Test
    public void getUserTestHttpStatusBAD_REQUEST() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        ResponseEntity response = vkUsersController.getUser("token", new VkUserAndGroupIdRequestDto(1111, 1111), bindingResult);
        Assert.assertTrue(CoreMatchers.is(response.getStatusCode()).matches(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void getUserTestBody() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        VkUserAndGroupIdRequestDto vkUserAndGroupIdRequestDto = new VkUserAndGroupIdRequestDto(78385, 93559769);
        String token = "token";
        VkUser vkUser = VkUser.builder().first_name("Иванов").last_name("Иван").nickname("Иванович").isMember(true).id(78385).build();
        VkUserDto vkUserDto = VkUserDto.builder().first_name("Иванов").last_name("Иван").nickname("Иванович").isMember(true).build();

        when(vkService.getUser(vkUserAndGroupIdRequestDto, token)).thenReturn(vkUser);
        when(convert.toVKUserDto(vkUser)).thenReturn(vkUserDto);
        ResponseEntity response = vkUsersController.getUser(token, vkUserAndGroupIdRequestDto, bindingResult);
        Assert.assertEquals(response.getBody(), vkUserDto);
        Assert.assertTrue(CoreMatchers.is(response.getStatusCode()).matches(HttpStatus.OK));
    }

//    @Test
//    public void emptyTokenAndBody() throws Exception {
//        this.mockMvc.perform(post("/api/vk/user/")).andExpect(status().isBadRequest());
//    }

//    @Test
//    public void emptyBody() throws Exception {
//        this.mockMvc.perform(post("/api/vk/user/").header("vk_service_token", "token"))
//                .andExpect(status().isBadRequest());
//    }

//    @Test
//    public void getOk() throws Exception {
//        String jsonString = "{\"user_id\": \"4\", \"group_id\": \"2\"}";
//        this.mockMvc.perform(post("/api/vk/user/")
//                .content(jsonString).contentType(MediaType.APPLICATION_JSON)
//                .header("vk_service_token", "token"))
//                .andExpect(status().isOk());
//    }

}
