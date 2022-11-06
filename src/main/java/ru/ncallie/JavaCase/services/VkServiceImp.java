package ru.ncallie.JavaCase.services;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.ncallie.JavaCase.dto.VkUserAndGroupIdRequestDto;
import ru.ncallie.JavaCase.exceptions.UserNotFoundException;
import ru.ncallie.JavaCase.models.VkUser;
import ru.ncallie.JavaCase.repositories.VkRepository;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class VkServiceImp implements VkService {
    VkRepository vkRepository;

    @Override
    public VkUser getUser(VkUserAndGroupIdRequestDto requestBody, String token) {
        VkUser vkUser = vkRepository.getUserById(requestBody.getUser_id(), token);
        if (vkUser.getDeactivated() != null)
            throw new UserNotFoundException("User " + vkUser.getDeactivated() + " or does not exist");
        vkUser.setMember(vkRepository.isMember(requestBody.getUser_id(), requestBody.getGroup_id(), token));
        return vkUser;
    }
}
