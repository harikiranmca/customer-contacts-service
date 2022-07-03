package com.telco.customer.contacts.utils;

import org.junit.jupiter.api.ClassDescriptor;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.ClassOrdererContext;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;

public class CustomerTestClassOrder implements ClassOrderer {
    @Override
    public void orderClasses(ClassOrdererContext context) {
        context.getClassDescriptors().sort(Comparator.comparingInt(CustomerTestClassOrder::getOrder));
    }

    private static int getOrder(ClassDescriptor classDescriptor) {
        if (classDescriptor.findAnnotation(SpringBootTest.class).isPresent()) {
            return 2;
        } else {
            return 1;
        }
    }
}
