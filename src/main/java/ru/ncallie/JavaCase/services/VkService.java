package ru.ncallie.JavaCase.services;

import ru.ncallie.JavaCase.dto.VkUserAndGroupIdRequestDto;
import ru.ncallie.JavaCase.models.VkUser;

public interface VkService {
    VkUser getUser(VkUserAndGroupIdRequestDto requestBody, String token);
}
