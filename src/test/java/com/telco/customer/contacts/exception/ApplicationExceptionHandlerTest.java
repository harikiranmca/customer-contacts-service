package com.telco.customer.contacts.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationExceptionHandlerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private ConstraintViolationException constraintViolationException;

    @InjectMocks
    private ApplicationExceptionHandler exceptionHandler;

    @Test
    public void handleConstraintViolationException_shouldHandleConstraintViolationException() {
        String mockPath = "/dummy/path";
        when(request.getRequestURI()).thenReturn(mockPath);
        Map<String, Object> map = exceptionHandler.handleConstraintViolationException(constraintViolationException, request);
        assertEquals(400, map.get("status"));
        assertEquals("Bad Request", map.get("error"));
        assertEquals(mockPath, map.get("path"));
    }
}
