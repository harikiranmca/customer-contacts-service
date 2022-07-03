package com.telco.customer.contacts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telco.customer.contacts.dto.PatchPhoneStatusRequest;
import com.telco.customer.contacts.dto.PhoneAction;
import com.telco.customer.contacts.repository.CustomerPhonesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;

import static com.telco.customer.contacts.utils.DataUtils.createPhoneByIdWithRandomData;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PhonesController.class)
public class PhonesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CustomerPhonesRepository customerPhonesRepository;

    @Autowired

    private ObjectMapper objectMapper;

    @Test
    public void getAllPhones_whenTheRequestIsValid_shouldReturn200WithListOfPhones() throws Exception {
        when(customerPhonesRepository.getAllPhones()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/api/v1/phones").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andExpect(jsonPath("$", empty()));
        verify(customerPhonesRepository, times(1)).getAllPhones();
    }


    @Test
    public void getAllPhonesByCustomer_whenTheRequestIsValid_shouldReturn200WithListOfPhonesByCustomer() throws Exception {
        String customerId = "123";
        when(customerPhonesRepository.getAllPhonesByCustomer(customerId)).thenReturn(Collections.singletonList(createPhoneByIdWithRandomData(customerId)));
        mockMvc.perform(get(String.format("/api/v1/customers/%s/phones", customerId)).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].phoneId", is("phone-123")))
                .andExpect(jsonPath("$.[0].countryCode", is("61")))
                .andExpect(jsonPath("$.[0].number", is("1234567890")))
                .andExpect(jsonPath("$.[0].status", is("CREATED")));

        verify(customerPhonesRepository, times(1)).getAllPhonesByCustomer(customerId);
    }

    @Test
    public void updatePhoneStatus_whenTheRequestIsValid_shouldReturn204() throws Exception {
        String phoneId = "phone-123";
        PatchPhoneStatusRequest patchPhoneStatusRequest = PatchPhoneStatusRequest.builder().action(PhoneAction.ACTIVATE).build();
        String requestBody = objectMapper.writeValueAsString(patchPhoneStatusRequest);

        doNothing().when(customerPhonesRepository).updatePhoneStatus(phoneId, patchPhoneStatusRequest);
        mockMvc.perform(patch(String.format("/api/v1/phones/%s", phoneId)).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody))
                .andExpect(status().isNoContent());
        verify(customerPhonesRepository, times(1)).updatePhoneStatus(any(String.class), any(PatchPhoneStatusRequest.class));
    }
}
