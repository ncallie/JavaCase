package ru.ncallie.JavaCase.exceptions;

public final class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
