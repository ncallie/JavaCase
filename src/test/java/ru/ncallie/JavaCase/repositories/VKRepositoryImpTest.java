package ru.ncallie.JavaCase.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.ncallie.JavaCase.dto.VkResponseDto;
import ru.ncallie.JavaCase.models.User;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VKRepositoryImpTest {

    @Autowired
    private VkRepository vkRepository;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void getUserByIdTest() {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.vk.com/method/users.get")
                .queryParam("user_ids", 78385)
                .queryParam("fields", "nickname")
                .queryParam("access_token", "token")
                .queryParam("v", "5.131")
                .build().toString();
        VkResponseDto vkResponseDto = new VkResponseDto();
        User user = User.builder().first_name("Иванов").last_name("Иван").nickname("Иванович").isMember(true).id(78385).build();
        vkResponseDto.setResponse(Collections.singletonList(user));
        when(restTemplate.getForObject(url , VkResponseDto.class)).thenReturn(vkResponseDto);
        User userRepo = vkRepository.getUserById(78385, "token");
        Assert.assertEquals(userRepo, user);
    }

    @Test
    public void isMemberTest() {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.vk.com/method/groups.isMember")
                .queryParam("group_id", 18936664)
                .queryParam("user_id", 61234563)
                .queryParam("access_token", "token")
                .queryParam("v", "5.131")
                .build().toString();
        when(restTemplate.getForObject(url , String.class)).thenReturn("{\"response\":1}");
        Assert.assertTrue(vkRepository.isMember(61234563, 18936664, "token"));

        when(restTemplate.getForObject(url , String.class)).thenReturn("{\"response\":0}");
        Assert.assertFalse(vkRepository.isMember(61234563, 18936664, "token"));

        when(restTemplate.getForObject(url , String.class)).thenReturn("");
        Assert.assertFalse(vkRepository.isMember(61234563, 18936664, "token"));

        when(restTemplate.getForObject(url , String.class)).thenReturn("{\"response\":5}");
        Assert.assertFalse(vkRepository.isMember(61234563, 18936664, "token"));
    }
}