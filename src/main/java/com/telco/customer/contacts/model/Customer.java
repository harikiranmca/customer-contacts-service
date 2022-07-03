package com.telco.customer.contacts.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class Customer {
    private String customerId;
    private String firstName;
    private String lastName;
    private List<Phone> phones;
}
