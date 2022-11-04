package ru.ncallie.JavaCase.dto;

import lombok.Getter;
import lombok.Setter;
import ru.ncallie.JavaCase.models.VkUser;

import java.util.List;

@Getter
@Setter
public class VkResponseDto {
    private List<VkUser> response;
}
