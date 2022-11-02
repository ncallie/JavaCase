package ru.ncallie.JavaCase.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor
public class UserAndGroupIdRequestDto {
    @Positive
    @NotNull
    private Integer user_id;
    @Positive
    @NotNull
    private Integer group_id;
}
