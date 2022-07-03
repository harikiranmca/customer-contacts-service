package com.telco.customer.contacts.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Customer {
    private final String customerId;
    private final String firstName;
    private final String lastName;
    private final List<Phone> phones;
}
