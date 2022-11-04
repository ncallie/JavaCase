package ru.ncallie.JavaCase.services;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ncallie.JavaCase.dto.VkUserAndGroupIdRequestDto;
import ru.ncallie.JavaCase.exceptions.UserNotFoundException;
import ru.ncallie.JavaCase.models.VkUser;
import ru.ncallie.JavaCase.models.VkUserStatus;
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
        VkUserAndGroupIdRequestDto vkUserAndGroupIdRequestDto = new VkUserAndGroupIdRequestDto(78385, 93559769);
        VkUser vkUser = VkUser.builder().first_name("Иванов").last_name("Иван").nickname("Иванович").id(78385).build();
        String token = "token";

        when(vkRepository.getUserById(vkUserAndGroupIdRequestDto.getUser_id(), token)).thenReturn(vkUser);

        when(vkRepository.isMember(vkUserAndGroupIdRequestDto.getUser_id(), vkUserAndGroupIdRequestDto.getGroup_id(), token)).thenReturn(true);
        VkUser vkUserFromService = vkService.getUser(vkUserAndGroupIdRequestDto, token);
        Assert.assertTrue(vkUserFromService.isMember());

        when(vkRepository.isMember(vkUserAndGroupIdRequestDto.getUser_id(), vkUserAndGroupIdRequestDto.getGroup_id(), token)).thenReturn(false);
        vkUserFromService = vkService.getUser(vkUserAndGroupIdRequestDto, token);
        Assert.assertEquals(vkUser, vkUserFromService);


        vkUser.setDeactivated(VkUserStatus.banned);
        Assert.assertThrows(UserNotFoundException.class, () -> vkService.getUser(vkUserAndGroupIdRequestDto, token));
    }
}