package ru.ncallie.JavaCase.repositories;

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

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VKRepositoryImpTest {

    @Autowired
    private VkRepository vkRepository;

    @MockBean
    private RestTemplate restTemplate;


    private String isMember = "https://api.vk.com/method/groups.isMember?group_id=%d&user_id=%d&access_token=%s&v=5.131";
    private String getUsers = "https://api.vk.com/method/users.get?user_ids=%d&fields=nickname&access_token=%s&v=5.131";
    @Test
    public void getUserByIdTest() {
        String url = String.format(getUsers, 78385, "token");
        VkResponseDto vkResponseDto = new VkResponseDto();
        User user = User.builder().first_name("Иванов").last_name("Иван").nickname("Иванович").isMember(true).id(78385).build();
        vkResponseDto.setResponse(Collections.singletonList(user));
        when(restTemplate.getForObject(url , VkResponseDto.class)).thenReturn(vkResponseDto);
        User userRepo = vkRepository.getUserById(78385, "token");
        Assert.assertEquals(userRepo, user);
    }

    @Test
    public void isMemberTrue() {
        String url = String.format(isMember, 18936663, 61234563, "token");
        when(restTemplate.getForObject(url , String.class)).thenReturn("{\"response\":1}");
        Assert.assertTrue(vkRepository.isMember(61234563, 18936663, "token"));
    }

    @Test
    public void isMemberFalse() {
        String url = String.format(isMember, 18936621, 61234553, "token");
        when(restTemplate.getForObject(url , String.class)).thenReturn("{\"response\":0}");
        Assert.assertFalse(vkRepository.isMember(61234553, 18936621, "token"));
    }

    @Test
    public void isMemberEmpty() {
        String url = String.format(isMember, 21936621, 32234553, "token");
        when(restTemplate.getForObject(url , String.class)).thenReturn("");
        Assert.assertFalse(vkRepository.isMember(32234553, 21936621, "token"));
    }
    @Test
    public void isMemberUndef() {
        String url = String.format(isMember, 219566621, 36534553, "token");
        when(restTemplate.getForObject(url , String.class)).thenReturn("{\"response\":5}");
        Assert.assertFalse(vkRepository.isMember(36534553, 219566621, "token"));
    }
}