package ru.ncallie.JavaCase.exceptions;

import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class VkApiException extends RuntimeException {
    Integer code;
    String msg;

    public VkApiException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
