package ru.ncallie.JavaCase.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@Schema(name = "VkRequest")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class VkUserAndGroupIdRequestDto {
    @Positive @NotNull
    @Schema(description = "User identifier", example = "205080632", minimum = "1", maximum = "2147483647", required = true)
    Integer user_id;
    @Positive @NotNull
    @Schema(description = "Group identifier", example = "63731512", minimum = "1", maximum = "2147483647", required = true)
    Integer group_id;
}
