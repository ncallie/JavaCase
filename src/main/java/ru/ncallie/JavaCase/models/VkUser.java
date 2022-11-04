package ru.ncallie.JavaCase.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VkUser {
    private Integer id;
    private String first_name;
    private String last_name;
    private VkUserStatus deactivated;
    private String nickname;
    private boolean isMember;
}

