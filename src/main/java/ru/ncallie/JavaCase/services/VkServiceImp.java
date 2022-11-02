package ru.ncallie.JavaCase.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ncallie.JavaCase.dto.UserAndGroupIdRequestDto;
import ru.ncallie.JavaCase.models.User;
import ru.ncallie.JavaCase.repositories.VkRepository;

@Service
@RequiredArgsConstructor
public class VkServiceImp implements VkService {
    private final VkRepository vkRepository;

    @Override
    public User getUser(UserAndGroupIdRequestDto requestBody, String token) {
        User user = vkRepository.getUserById(requestBody.getUser_id(), token);
        user.setMember(vkRepository.isMember(requestBody.getUser_id(), requestBody.getGroup_id(), token));
        return user;
    }
}
