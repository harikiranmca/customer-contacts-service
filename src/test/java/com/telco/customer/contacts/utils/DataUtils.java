package com.telco.customer.contacts.utils;

import com.telco.customer.contacts.model.Customer;
import com.telco.customer.contacts.model.Phone;
import com.telco.customer.contacts.model.PhoneStatus;

import java.util.Collections;

public class DataUtils {

    public static Phone createPhoneByIdWithRandomData(String id) {
        return Phone.builder().phoneId("phone-" + id).countryCode("61").number("1234567890")
                .status(PhoneStatus.CREATED).build();
    }

    public static Customer createCustomerByIdWithRandomData(String id) {
        return Customer.builder().customerId("customer-" + id).firstName("firstName1").lastName("lastName1")
                .phones(Collections.singletonList(createPhoneByIdWithRandomData(id))).build();
    }

}
