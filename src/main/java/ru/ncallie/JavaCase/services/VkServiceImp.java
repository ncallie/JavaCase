package ru.ncallie.JavaCase.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ncallie.JavaCase.dto.VkUserAndGroupIdRequestDto;
import ru.ncallie.JavaCase.exceptions.UserNotFoundException;
import ru.ncallie.JavaCase.models.VkUser;
import ru.ncallie.JavaCase.repositories.VkRepository;

@Service
@RequiredArgsConstructor
public class VkServiceImp implements VkService {
    private final VkRepository vkRepository;

    @Override
    public VkUser getUser(VkUserAndGroupIdRequestDto requestBody, String token) {
        VkUser vkUser = vkRepository.getUserById(requestBody.getUser_id(), token);
        if (vkUser.getDeactivated() != null)
                throw new UserNotFoundException("User " + vkUser.getDeactivated() + " or does not exist");
        vkUser.setMember(vkRepository.isMember(requestBody.getUser_id(), requestBody.getGroup_id(), token));
        return vkUser;
    }
}
