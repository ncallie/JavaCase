package ru.ncallie.JavaCase.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.ncallie.JavaCase.models.VkUser;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter @Setter
@FieldDefaults(level = PRIVATE)
public final class VkResponseDto {
    List<VkUser> response;
}
