package ru.ncallie.JavaCase.exceptions;

import lombok.Getter;

@Getter
public class VkApiException extends RuntimeException {
    private Integer code;
    private String msg;

    public VkApiException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
