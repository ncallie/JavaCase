package ru.ncallie.JavaCase.exceptions;

import lombok.Getter;

@Getter
public class VkApiException extends RuntimeException {
    private String code;
    private String msg;

    public VkApiException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
