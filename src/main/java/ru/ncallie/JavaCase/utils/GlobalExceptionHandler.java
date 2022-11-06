package ru.ncallie.JavaCase.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.ncallie.JavaCase.exceptions.DataInUseException;
import ru.ncallie.JavaCase.exceptions.UserNotFoundException;
import ru.ncallie.JavaCase.exceptions.VkApiException;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("error_code", "100");
        if (exception.getMessage().contains("Overflow: numeric value"))
            errors.put("error_msg", "Numeric value out of range (-2147483648 -2147483647)");
        else if (exception.getMessage().contains("Cannot deserialize value of type `java.lang.Integer` from String"))
            errors.put("error_msg", "Cannot deserialize value of type Integer from String");
        else
            errors.put("undefined expressions", exception.getMessage());
        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleException(UserNotFoundException exception, HttpServletRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("error_code", "18");
        errors.put("error_msg", exception.getMessage());
        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VkApiException.class)
    public ResponseEntity<String> handleException(VkApiException exception, HttpServletRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("error code", exception.getCode().toString());
        errors.put("error_msg", exception.getMsg());
        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleException(AuthenticationException exception, HttpServletRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("error code", "401");
        errors.put("error_msg", "You aren’t authenticated–either not authenticated at all or authenticated incorrectly–but please reauthenticate and try again");
        return new ResponseEntity(errors, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleException(AccessDeniedException exception, HttpServletRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("error code", "403");
        errors.put("error_msg", "Access is denied");
        return new ResponseEntity(errors, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DataInUseException.class)
    public ResponseEntity<String> handleException(DataInUseException exception, HttpServletRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("error code", "409");
        errors.put("error_msg", exception.getMessage());
        return new ResponseEntity(errors, HttpStatus.CONFLICT);
    }
}
