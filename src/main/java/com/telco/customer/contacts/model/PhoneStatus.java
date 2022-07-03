package com.telco.customer.contacts.model;

import com.telco.customer.contacts.dto.PhoneAction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum PhoneStatus {
    CREATED("Create"),
    ACTIVATED("Activate"),
    SUSPENDED("Suspend"),
    TERMINATED("Terminate");

    private String action;

    public static PhoneStatus getStatusByAction(PhoneAction action) {
        Optional<PhoneStatus> optionalPhoneStatus = Stream.of(PhoneStatus.values()).filter(phoneStatus -> phoneStatus.action.equals(action.getAction())).findFirst();
        return optionalPhoneStatus.orElse(null);
    }
}
