package ru.ncallie.JavaCase.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor
@Schema(name = "getUser")
public class UserAndGroupIdRequestDto {
    @Positive
    @NotNull
    @Schema(description = "User identifier", example = "205080632", minimum = "1", maximum = "2147483647", required = true)
    private Integer user_id;
    @Positive
    @NotNull
    @Schema(description = "Group identifier", example = "63731512", minimum = "1", maximum = "2147483647", required = true)
    private Integer group_id;
}
