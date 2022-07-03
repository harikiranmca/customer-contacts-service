package com.telco.customer.contacts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PhoneNotFoundException extends RuntimeException {
    public PhoneNotFoundException(String phoneId) {
        super(String.format("Phone by id %s is not found", phoneId));
    }
}
