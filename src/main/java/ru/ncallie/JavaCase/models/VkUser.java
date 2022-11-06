package ru.ncallie.JavaCase.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = PRIVATE)
public class VkUser {
    Integer id;
    String first_name;
    String last_name;
    VkUserStatus deactivated;
    String nickname;
    boolean isMember;
}

