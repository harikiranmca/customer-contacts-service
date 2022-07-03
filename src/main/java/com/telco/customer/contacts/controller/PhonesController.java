package com.telco.customer.contacts.controller;

import com.telco.customer.contacts.dto.PatchPhoneStatusRequest;
import com.telco.customer.contacts.model.Phone;
import com.telco.customer.contacts.repository.CustomerPhonesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class PhonesController {

    private final CustomerPhonesRepository customerPhonesRepository;

    @GetMapping("/phones")
    @ResponseStatus(HttpStatus.OK)
    public List<Phone> getAllPhones() {
        log.info("message=\"Retrieving all phones\"");
        return customerPhonesRepository.getAllPhones();
    }

    @PatchMapping("/phones/{phoneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePhoneStatus(@PathVariable String phoneId, @Valid @RequestBody PatchPhoneStatusRequest phoneStatusRequest) {
        log.info("message=\"Patching phone {} with new status {}\"", phoneId, phoneStatusRequest.getAction());
        customerPhonesRepository.updatePhoneStatus(phoneId, phoneStatusRequest);
    }

    @GetMapping("/customers/{customerId}/phones")
    @ResponseStatus(HttpStatus.OK)
    public List<Phone> getAllPhonesByCustomer(@PathVariable String customerId) {
        log.info("message=\"Retrieving phones by customer {}\"", customerId);
        return customerPhonesRepository.getAllPhonesByCustomer(customerId);
    }


}
