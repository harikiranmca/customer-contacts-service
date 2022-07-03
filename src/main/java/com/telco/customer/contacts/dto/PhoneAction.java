package com.telco.customer.contacts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PhoneAction {
    CREATE("Create"),
    ACTIVATE("Activate"),
    SUSPEND("Suspend"),
    TERMINATE("Terminate");

    private final String action;
}
