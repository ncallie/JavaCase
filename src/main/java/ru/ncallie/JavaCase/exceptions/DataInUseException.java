package ru.ncallie.JavaCase.exceptions;

public class DataInUseException extends RuntimeException {
    public DataInUseException(String s) {
        super(s);
    }
}
