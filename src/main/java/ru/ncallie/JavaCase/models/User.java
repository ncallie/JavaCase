package ru.ncallie.JavaCase.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    private Integer id;
    private String first_name;
    private String last_name;
    private UserStatus deactivated;
    private String nickname;
    private boolean isMember;
}

