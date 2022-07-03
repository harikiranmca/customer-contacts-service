package com.telco.customer.contacts.repository;


import com.telco.customer.contacts.dto.PatchPhoneStatusRequest;
import com.telco.customer.contacts.dto.PhoneAction;
import com.telco.customer.contacts.exception.CustomerNotFoundException;
import com.telco.customer.contacts.exception.PhoneNotFoundException;
import com.telco.customer.contacts.model.Customer;
import com.telco.customer.contacts.model.Phone;
import com.telco.customer.contacts.model.PhoneStatus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static com.telco.customer.contacts.utils.DataUtils.createCustomerByIdWithRandomData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//As we are manipulating an in-memory datastore, order annotations are used to assert the correctness of the functionality.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerPhonesRepositoryTest {

    CustomerPhonesRepository customerPhonesRepository = new CustomerPhonesRepository();
    Customer customer1 = createCustomerByIdWithRandomData("1");
    Customer customer2 = createCustomerByIdWithRandomData("2");

    @Test
    @Order(1)
    public void getAllPhones_whenNoPhonesPresent_shouldReturnEmptyList() {
        assertEquals(0, customerPhonesRepository.getAllPhones().size());
    }

    @Test
    @Order(2)
    public void getAllPhones_whenPhonesPresent_shouldReturnWholeList() {
        customerPhonesRepository.saveCustomer(customer1);
        assertEquals(1, customerPhonesRepository.getAllPhones().size());
        customerPhonesRepository.saveCustomer(customer2);
        assertEquals(2, customerPhonesRepository.getAllPhones().size());
    }

    @Test
    @Order(3)
    public void getAllPhonesByCustomer_whenPhonesPresent_shouldReturnPhones() {
        assertEquals(1, customerPhonesRepository.getAllPhonesByCustomer(customer1.getCustomerId()).size());
    }

    @Test
    @Order(4)
    public void getAllPhonesByCustomer_whenCustomerIsInvalid_shouldThrowCustomerNotFoundException() {
        assertThrows(CustomerNotFoundException.class, () -> customerPhonesRepository.getAllPhonesByCustomer("random-customer"));
    }

    @Test
    @Order(5)
    public void updatePhoneStatus_whenPhoneIsPresent_shouldUpdateThePhoneStatus() {
        //As the data is mocked, we know that there is one phone associated with this customer.
        Phone phone = customer1.getPhones().get(0);
        //Asserting initial status
        assertEquals(PhoneStatus.CREATED, phone.getStatus());
        PatchPhoneStatusRequest phoneStatusRequest = PatchPhoneStatusRequest.builder().action(PhoneAction.ACTIVATE).build();
        customerPhonesRepository.updatePhoneStatus(phone.getPhoneId(), phoneStatusRequest);
        List<Phone> updatedPhones = customerPhonesRepository.getAllPhonesByCustomer(customer1.getCustomerId());
        assertEquals(PhoneStatus.ACTIVATED, updatedPhones.get(0).getStatus());
    }

    @Test
    @Order(6)
    public void updatePhoneStatus_whenPhoneIsNotPresent_shouldThrowPhoneNotFoundException() {
        assertThrows(PhoneNotFoundException.class, () -> customerPhonesRepository.updatePhoneStatus("random-phone-id", PatchPhoneStatusRequest.builder().action(PhoneAction.ACTIVATE).build()));
    }

}
