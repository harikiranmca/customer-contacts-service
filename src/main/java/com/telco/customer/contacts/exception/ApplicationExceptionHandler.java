package com.telco.customer.contacts.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler {
    private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //We generally have more meaningful error model for the error responses. In this case limiting the error model
    //to default spring provided one.

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, Object> handleConstraintViolationException(ConstraintViolationException constraintViolationException, final HttpServletRequest request) {
        log.error("message= validation exception {}", constraintViolationException);
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now().format(format));
        map.put("status", HttpStatus.BAD_REQUEST.value());
        map.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        map.put("path", request.getRequestURI());
        return map;

    }

}
