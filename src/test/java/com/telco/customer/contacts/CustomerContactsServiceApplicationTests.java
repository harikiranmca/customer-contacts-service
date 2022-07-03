package com.telco.customer.contacts;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.http.ContentType;
import com.telco.customer.contacts.model.Phone;
import com.telco.customer.contacts.model.PhoneStatus;
import com.telco.customer.contacts.repository.CustomerPhonesRepository;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerContactsServiceApplicationTests {


    @LocalServerPort
    private int port;

    @Autowired
    CustomerPhonesRepository customerPhonesRepository;

    //Customer and phone Ids from pre-initialised datastore.
    String customer1 = "customer-1";
    String phoneId1 = "phone-1";


    @BeforeEach()
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.basePath = "/api/v1";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

    }

    @Test
    @Order(0)
    public void getPhonesApi_whenRequestIsValid_shouldReturnAllPhones() {

        Phone[] phones = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/phones")
                .as(Phone[].class);
        assertEquals(2, phones.length);

    }

    @Test
    @Order(1)
    public void getPhonesApi_whenInvalidContentType_shouldReturnUnsupportedMediaTypeError() {
        given()
                .contentType(ContentType.TEXT)
                .when()
                .get("/phones")
                .then()
                .statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);

    }


    @Test
    @Order(3)
    public void patchPhoneApi_whenInvalidContentType_shouldReturnUnsupportedMediaTypeError() {
        given()
                .contentType(ContentType.TEXT)
                .when()
                .patch(String.format("/phones/%s", phoneId1))
                .then()
                .statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);

    }

    @Test
    @Order(4)
    public void patchPhoneApi_whenInvalidPhoneId_shouldReturnBadRequest() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("action", "ACTIVATE");
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(requestMap)
                .patch("/phones/1")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);

    }

    @Test
    @Order(5)
    public void patchPhoneApi_whenPhoneIdNotFound_shouldReturnNotFound() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("action", "ACTIVATE");
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(requestMap)
                .patch("/phones/12")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @Order(6)
    public void patchPhoneApi_whenActionDoesntMatch_shouldReturnBadRequest() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("action", "RANDOM");
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(requestMap)
                .patch("/phones/12")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @Order(7)
    public void patchPhoneApi_whenRequestIsValid_shouldReturnNoContent() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("action", "ACTIVATE");
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(requestMap)
                .patch("/phones/" + phoneId1)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        //Verify if the phone is activated
        Phone[] phones = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/phones")
                .as(Phone[].class);
        assertEquals(2, phones.length);

        for (Phone phone : phones) {
            if (phone.getPhoneId().equals(phoneId1)) {
                assertEquals(PhoneStatus.ACTIVATED, phone.getStatus());
            }
        }
    }

    @Test
    @Order(8)
    public void getPhonesByCustomerApi_whenInvalidContentType_shouldReturnUnsupportedMediaTypeError() {
        given()
                .contentType(ContentType.TEXT)
                .when()
                .get(String.format("/customers/%s/phones", customer1))
                .then()
                .statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);

    }

    @Test
    @Order(9)
    public void getPhonesByCustomerApi_whenInvalidCustomerId_shouldReturnBadRequest() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(String.format("/customers/%s/phones", "1"))
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }


    @Test
    @Order(10)
    public void getPhonesByCustomerApi_whenCustomerIdNotFound_shouldReturnNotFound() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(String.format("/customers/%s/phones", "customer-random"))
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @Order(11)
    public void getPhonesByCustomerApi_whenCustomerIdIsvalid_shouldReturnListOfAssocaitedPhones() {
        Phone[] phones = given()
                .contentType(ContentType.JSON)
                .when()
                .get(String.format("/customers/%s/phones", customer1))
                .as(Phone[].class);
        assertEquals(1, phones.length);
    }

}
