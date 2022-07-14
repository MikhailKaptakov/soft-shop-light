package ru.soft1.soft_shop_light.configuration;

import javax.validation.groups.Default;

public class ValidationCustomer {
    // Validate only form UI/REST
    public interface Web extends Default {}

    // Validate only when DB save/update
    public interface Persist extends Default {}
}