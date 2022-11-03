package ru.ncallie.JavaCase.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.ncallie.JavaCase.exceptions.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("Error", "You gave an incorrect value");
        errors.put("Details", exception.getMessage());
        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleException(UserNotFoundException exception, HttpServletRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("Error", exception.getMessage());
        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }
}
