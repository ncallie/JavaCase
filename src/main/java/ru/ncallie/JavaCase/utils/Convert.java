package ru.ncallie.JavaCase.utils;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import ru.ncallie.JavaCase.dto.VkUserDto;
import ru.ncallie.JavaCase.models.VkUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class Convert {
    private final ModelMapper modelMapper;

    public Map<String, List<String>> toMessages(BindingResult bindingResult) {
        Map<String, List<String>> errors = new HashMap<>();
        for (ObjectError error : bindingResult.getAllErrors()) {
            String field = ((FieldError) error).getField();
            if (errors.containsKey(field)) errors.get(field).add(error.getDefaultMessage());
            else {
                List<String> msg = new ArrayList<>();
                msg.add(error.getDefaultMessage());
                errors.put(field, msg);
            }
        }
        return errors;
    }

    public VkUserDto toUserDto(VkUser vkUser) {
        return modelMapper.map(vkUser, VkUserDto.class);
    }
}
