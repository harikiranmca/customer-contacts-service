package com.telco.customer.contacts.repository;

import com.telco.customer.contacts.dto.PatchPhoneStatusRequest;
import com.telco.customer.contacts.exception.CustomerNotFoundException;
import com.telco.customer.contacts.exception.PhoneNotFoundException;
import com.telco.customer.contacts.model.Customer;
import com.telco.customer.contacts.model.Phone;
import com.telco.customer.contacts.model.PhoneStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@NoArgsConstructor(access = AccessLevel.NONE)
public class CustomerPhonesRepository {
    private static final List<Customer> CUSTOMER_PHONES_DB = new ArrayList<>();

    public List<Phone> getAllPhones() {
        log.info("message=\"Retrieving all phones\"");
        List<Phone> phones = new ArrayList<>();
        CUSTOMER_PHONES_DB.stream().map(Customer::getPhones).forEach(phones::addAll);
        return phones;
    }

    public List<Phone> getAllPhonesByCustomer(String customerId) {
        log.info("message=\"Retrieving phones by customer id {}\"", customerId);
        Optional<Customer> optionalCustomer = CUSTOMER_PHONES_DB.stream().filter(customer -> customer.getCustomerId().equals(customerId)).findAny();
        if (optionalCustomer.isEmpty()) {
            log.error("message=\"Invalid customer id {}\"", customerId);
            throw new CustomerNotFoundException(customerId);
        }
        return optionalCustomer.get().getPhones();
    }

    public void updatePhoneStatus(String phoneId, PatchPhoneStatusRequest phoneStatusRequest) {
        log.info("message=\"Performing action {} on phone id {}\"", phoneStatusRequest.getAction(), phoneId);
        Optional<Phone> optionalPhone = CUSTOMER_PHONES_DB.stream().flatMap(customer -> customer.getPhones().stream()).filter(phone -> phone.getPhoneId().equals(phoneId)).findAny();
        if (optionalPhone.isEmpty()) {
            log.error("message=\"Invalid phone id {}\"", phoneId);
            throw new PhoneNotFoundException(phoneId);
        }
        PhoneStatus newPhoneStatus = PhoneStatus.getStatusByAction(phoneStatusRequest.getAction());
        optionalPhone.get().setStatus(newPhoneStatus);

    }

    //This method is added for adding test data.
    public Customer saveCustomer(Customer customer) {
        log.info("message=\"Saving customer \"", customer.getCustomerId());
        CUSTOMER_PHONES_DB.add(customer);
        return customer;
    }

//    @PostConstruct
//    public void setupData() {
//        Phone phone = Phone.builder().phoneId("some-uuid").countryCode("61").number("123456789").status(PhoneStatus.CREATED).build();
//        List<Phone> phones = new ArrayList<>();
//        phones.add(phone);
//        this.saveCustomer(Customer.builder().firstName("Customer 1 first name").lastName("Customer 2 last name").phones(phones).build());
//    }
}
