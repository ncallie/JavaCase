package ru.ncallie.JavaCase.exceptions;

public final class DataInUseException extends RuntimeException {
    public DataInUseException(String s) {
        super(s);
    }
}
