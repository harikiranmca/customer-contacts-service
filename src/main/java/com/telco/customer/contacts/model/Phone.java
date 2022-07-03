package com.telco.customer.contacts.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Phone {
    private String phoneId;
    private String countryCode;
    private String number;
    private PhoneStatus status;
}
