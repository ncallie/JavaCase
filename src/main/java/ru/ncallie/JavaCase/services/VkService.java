package ru.ncallie.JavaCase.services;

import ru.ncallie.JavaCase.dto.UserAndGroupIdRequestDto;
import ru.ncallie.JavaCase.models.User;

public interface VkService {
    User getUser(UserAndGroupIdRequestDto requestBody, String token);
}
