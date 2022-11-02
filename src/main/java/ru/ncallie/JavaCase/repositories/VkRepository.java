package ru.ncallie.JavaCase.repositories;

import ru.ncallie.JavaCase.models.User;

public interface VkRepository {
    User getUserById(Integer user_id, String token);
    boolean isMember(Integer user_id, Integer group_id, String token);
}
