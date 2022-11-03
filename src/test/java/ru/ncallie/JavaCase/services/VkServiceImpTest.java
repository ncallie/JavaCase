package ru.ncallie.JavaCase.services;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ncallie.JavaCase.dto.UserAndGroupIdRequestDto;
import ru.ncallie.JavaCase.exceptions.UserNotFoundException;
import ru.ncallie.JavaCase.models.User;
import ru.ncallie.JavaCase.models.UserStatus;
import ru.ncallie.JavaCase.repositories.VkRepository;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class VkServiceImpTest {

    @Autowired
    private VkService vkService;

    @MockBean
    private VkRepository vkRepository;

    @Test
    void getUserTest() {
        UserAndGroupIdRequestDto userAndGroupIdRequestDto = new UserAndGroupIdRequestDto(78385, 93559769);
        User user = User.builder().first_name("Иванов").last_name("Иван").nickname("Иванович").id(78385).build();
        String token = "token";

        when(vkRepository.getUserById(userAndGroupIdRequestDto.getUser_id(), token)).thenReturn(user);

        when(vkRepository.isMember(userAndGroupIdRequestDto.getUser_id(), userAndGroupIdRequestDto.getGroup_id(), token)).thenReturn(true);
        User userFromService = vkService.getUser(userAndGroupIdRequestDto, token);
        Assert.assertTrue(userFromService.isMember());

        when(vkRepository.isMember(userAndGroupIdRequestDto.getUser_id(), userAndGroupIdRequestDto.getGroup_id(), token)).thenReturn(false);
        userFromService = vkService.getUser(userAndGroupIdRequestDto, token);
        Assert.assertEquals(user, userFromService);


        user.setDeactivated(UserStatus.banned);
        Assert.assertThrows(UserNotFoundException.class, () -> vkService.getUser(userAndGroupIdRequestDto, token));
    }
}